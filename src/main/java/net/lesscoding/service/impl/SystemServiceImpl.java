package net.lesscoding.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.entity.Account;
import net.lesscoding.model.dto.AccountDto;
import net.lesscoding.model.vo.RedisUserCache;
import net.lesscoding.service.AccountService;
import net.lesscoding.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author eleven
 * @date 2023/10/8 12:14
 * @apiNote
 */
@Service
@Slf4j
public class SystemServiceImpl implements SystemService {
    @Autowired
    private AccountService accountService;

    @Autowired
    @Qualifier("jedisRedisTemplate")
    private RedisTemplate jedisTemplate;

    @Value("${redis.userCache}")
    private String userCache;

    @Autowired
    private Gson gson;

    @Override
    public Object registerAccount(AccountDto dto) {
        return accountService.registerAccount(dto);
    }

    @Override
    @Scheduled(cron = "0 0/15 * * * ?")
    public Integer autoRegisterByRedisMac() {
        List<Account> accountList = accountService.getAccountList();
        Map<String,String> userCacheMap = jedisTemplate.boundHashOps(userCache).entries();
        Set<String> redisMacSet = userCacheMap.keySet();
        if (CollUtil.isNotEmpty(accountList)) {
            List<String> existsMacList = accountList.stream()
                    .map(Account::getMac)
                    .collect(Collectors.toList());

            // 获取到没有注册的mac地址
            existsMacList.forEach(redisMacSet::remove);
        }
        List<Account> insertList = new ArrayList<>();
        for (String redisMac : redisMacSet) {
            RedisUserCache userVo = gson.fromJson(userCacheMap.get(redisMac), RedisUserCache.class);
            Account account = new Account(userVo);
            insertList.add(account);
        }

        if (CollUtil.isNotEmpty(insertList)) {
            log.info("当前要自动创建账号数量为 -{}", insertList.size());
            accountService.saveBatch(insertList, 50);
        }
        return redisMacSet.size();
    }

    @Override
    public Object doLogin(AccountDto dto) {
        return accountService.doLogin(dto);
    }

}
