package net.lesscoding.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.Account;
import net.lesscoding.exception.AccountException;
import net.lesscoding.mapper.AccountMapper;
import net.lesscoding.model.dto.AccountDto;
import net.lesscoding.service.AccountService;
import net.lesscoding.utils.IpUtils;
import net.lesscoding.utils.PasswordUtil;
import net.lesscoding.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
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

    //@Autowired
    //@Qualifier("myRedisTemplate")
    //private RedisTemplate redisTemplate;
    @Override
    public List<Account> getAccountList() {
        return accountMapper.selectList(null);
    }

    @NacosValue("${redis.userNameCache}")
    private String userNameCache;

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

    /**
     * 每十五分钟从redis中获取当前的登录用户，
     * 如果当前mac没有注册，自动注册用户
     */
    @Override
    @Scheduled(cron = "0 0/15 * * * ?")
    public void autoRegisterByMac() {
        //Object o = redisTemplate.opsForValue().get(userNameCache);
        //System.out.println(o);
    }

    public String quickLoginByMac(AccountDto dto) {
        String mac = dto.getMac();
        if (StrUtil.isBlank(mac)) {
            mac = IpUtils.getMac(ServletUtils.getRequest());
        }
        Account account = accountMapper.selectOne(new QueryWrapper<Account>()
                .eq("del_flag", false)
                .eq("mac", mac)
                .or().
                eq("ip", IpUtils.getIpAddr(ServletUtils.getRequest())));
        if (account == null) {
            throw new RuntimeException("查询账号失败，请联系塘主处理，QQ群号754126966");
        }
        if (account != null && account.getStatus() != 0) {
            StpUtil.login(account.getId());
        }
        return StpUtil.getTokenValueByLoginId(account.getId());
    }

    @Override
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
        if (row.getStatus() == 1) {
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
