package net.lesscoding.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import net.lesscoding.entity.Account;
import net.lesscoding.exception.AccountException;
import net.lesscoding.mapper.AccountMapper;
import net.lesscoding.model.dto.AccountDto;
import net.lesscoding.model.dto.UpdatePwdDto;
import net.lesscoding.model.vo.RedisUserCache;
import net.lesscoding.service.AccountPlayerService;
import net.lesscoding.service.AccountService;
import net.lesscoding.utils.AccountUtil;
import net.lesscoding.utils.IpUtils;
import net.lesscoding.utils.PasswordUtil;
import net.lesscoding.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author eleven
 * @date 2023/9/27 15:54
 * @apiNote
 */
@Service
@RefreshScope
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountPlayerService playerService;

    @Autowired
    @Qualifier("jedisRedisTemplate")
    private RedisTemplate jedisTemplate;

    @Override
    public List<Account> getAccountList() {
        return accountMapper.selectList(null);
    }

    @Value("${redis.userCache}")
    private String userCache;

    @Autowired
    private Gson gson;

    @Autowired
    private AccountUtil accountUtil;

    @Override
    public String registerAccount(Account account) {
        //List<String> allAccount = (List<String>) redisTemplate.opsForValue().get("allAccount");

        List<Account> accounts = accountMapper.selectList(new QueryWrapper<Account>()
                .eq("del_flag", false)
        );
        List<String> accountStrList =accounts.stream()
                .map(Account::getAccount)
                .collect(Collectors.toList());
        String accountStr = "";
        do {
            accountStr = String.valueOf(RandomUtil.randomInt(9_999, 100_000));
        } while (!accountStr.contains(accountStr));
        System.out.println(accountStr);
        return accountStr;
    }

    /**
     * 注册账号
     * @param dto
     * @return
     */
    @Override
    public Object registerAccount(AccountDto dto) {
        return null;
    }

    public String quickLoginByMac(AccountDto dto) {
        String mac = dto.getMac();
        if (StrUtil.isBlank(mac)) {
            mac = IpUtils.getMac(ServletUtils.getRequest());
        }
        Account account = accountMapper.selectOne(new QueryWrapper<Account>()
                .eq("del_flag", false)
                .eq("mac", mac));
        if (account == null) {
            Map<String,String> userCacheMap = jedisTemplate.boundHashOps(userCache).entries();
            RedisUserCache userVo = gson.fromJson(userCacheMap.get(mac), RedisUserCache.class);
            Set<String> accountSet = accountMapper.selectAccountSet(null);
            Account entity = new Account(userVo);
            account.setAccount(accountUtil.getAccountStr(accountSet));
            account.setPassword(PasswordUtil.encrypt(account.getAccount(), account.getSalt()));
            accountMapper.insert(entity);
            playerService.addPlayerByAccount(Collections.singletonList(entity));
            StpUtil.login(entity.getId());
            return StpUtil.getTokenValueByLoginId(entity.getId());
        }
        if (account.getStatus() != 0) {
            StpUtil.login(account.getId());
        }
        return StpUtil.getTokenValueByLoginId(account.getId());
    }



    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Object doLogin(AccountDto dto) {
        Integer type = dto.getType();
        if (type == null) {
            throw new RuntimeException("登录类型不允许为空");
        }
        switch (type) {
            case 0:
                return quickLoginByMac(dto);
            case 1:
                return loginByAccount(dto);
            default:
                throw new RuntimeException("登录类型异常");
        }
    }

    @Override
    public Integer updateAccountPwd(UpdatePwdDto dto) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<Account>();
        String mac = dto.getMac();
        if (StrUtil.isBlank(mac)) {
            queryWrapper.eq("id", StpUtil.getLoginId());
        } else  {
            queryWrapper.eq("mac", mac);
        }
        Account account = accountMapper.selectOne(queryWrapper);
        if (account == null) {
            throw AccountException.delException();
        }
        Boolean accessFlag = PasswordUtil.decrypt(account.getPassword(), account.getSalt(), dto.getOldPwd());
        if (!accessFlag) {
            throw AccountException.notAllow();
        }


        String newPwd = dto.getNewPwd();
        if (StrUtil.isBlank(newPwd)) {
            throw new RuntimeException("新密码不允许为空");
        }
        String encryptPwd = PasswordUtil.encrypt(newPwd, account.getSalt());
        account.setPassword(encryptPwd);
        String newAccount = dto.getNewAccount();
        if (StrUtil.isNotBlank(newAccount)) {
            account.setAccount(newAccount);
            boolean accountExistFlag = accountMapper.selectAccountIfExist(account.getId(), dto.getNewAccount());
            if (accountExistFlag) {
                throw AccountException.accountExists();
            }

        }
        return accountMapper.updateById(account);
    }

    private Object loginByAccount(AccountDto dto) {
        String account = dto.getAccount();
        String password = dto.getPassword();
        if (StrUtil.isBlank(account) || StrUtil.isBlank(password)) {
            throw new RuntimeException("账号货密码不允许为空");
        }
        Account row = accountMapper.selectOne(new QueryWrapper<Account>()
                .eq("account", account));
        if (row == null || row.getDelFlag()) {
            throw AccountException.delException();
        }
        if (row.getStatus() == 2) {
            throw AccountException.singOut();
        }
        if (row.getStatus() == 0) {
            throw AccountException.blackList();
        }

        Boolean accessFlag = PasswordUtil.decrypt(row.getPassword(), row.getSalt(), password);
        if (!accessFlag) {
            throw AccountException.notAllow();
        }
        StpUtil.login(row.getId());
        return StpUtil.getTokenValueByLoginId(row.getId());
    }
}
