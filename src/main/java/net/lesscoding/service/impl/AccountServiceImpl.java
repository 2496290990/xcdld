package net.lesscoding.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.config.RedisConfig;
import net.lesscoding.entity.Account;
import net.lesscoding.mapper.AccountMapper;
import net.lesscoding.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author eleven
 * @date 2023/9/27 15:54
 * @apiNote
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public List<Account> getAccountList() {
        return null;
    }

    @Override
    public String registerAccount(Account account) {
        List<String> allAccount = (List<String>) redisTemplate.opsForValue().get("allAccount");

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
}
