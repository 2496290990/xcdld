package net.lesscoding.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.Account;
import net.lesscoding.mapper.AccountMapper;
import net.lesscoding.service.AccountService;
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
        return null;
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
     * 每十五分钟从redis中获取当前的登录用户，
     * 如果当前mac没有注册，自动注册用户
     */
    @Override
    @Scheduled(cron = "0 0/15 * * * ?")
    public void autoRegisterByMac() {
        //Object o = redisTemplate.opsForValue().get(userNameCache);
        //System.out.println(o);
    }
}
