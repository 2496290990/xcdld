package net.lesscoding;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.lesscoding.entity.Account;
import net.lesscoding.mapper.AccountMapper;
import net.lesscoding.service.AccountService;
import net.lesscoding.service.SystemService;
import net.lesscoding.utils.PasswordUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author eleven
 * @date 2023/9/27 16:46
 * @apiNote
 */
@SpringBootTest(classes = MainApp.class)
@RunWith(SpringRunner.class)
@RefreshScope
public class AccountTest {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private SystemService systemService;

    @Value("${redis.userNameCache}")
    private String userNameCache;

    @Test
    public void randomAccount() {
        int cycle = 50;
        for (int i = 0; i < cycle; i++) {
            accountService.registerAccount(new Account());
        }
    }

    @Test
    public void insertAccountTest() {

        Account entity = new Account();
        entity.setCreateTime(LocalDateTime.now());
        entity.setMac(getLocalMac());
        accountMapper.insert(entity);
    }

    private String getLocalMac() {
        String macStr = "";
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            macStr = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return macStr;
    }


    @Resource(name = "jedisRedisTemplate")
    private RedisTemplate redisTemplate;


    @Test
    public void userNameCache() {

        Object o = redisTemplate.opsForHash().get("USER_NAME_CACHE", "翻译官");
        System.out.println(o);
        System.out.println();

        Map entries = redisTemplate.boundHashOps(userNameCache).entries();
        entries.forEach((k, v) -> System.out.println(k + " \t==> " + v));

    }
    @Test
    public void autoRegisterByMacTest() {
        System.out.println(systemService.autoRegisterByRedisMac());
    }

    @Test
    public void autoInsertAccount() {
        List<Account> accounts = accountMapper.selectList(new QueryWrapper<Account>()
                .ge("id", 0));
        Set<String> accountSet = new HashSet<>();
        do {
            accountSet.add(String.valueOf(RandomUtil.randomInt(9_999, 100_000)));
        } while (accounts.size() != accountSet.size());
        List<String> list = new ArrayList<>(accountSet);
        for (int i = 0; i < accounts.size(); i++) {
            Account item = accounts.get(i);
            item.setAccount(list.get(i));
            String salt = PasswordUtil.salt();
            item.setSalt(salt);
            item.setPassword(PasswordUtil.encrypt(list.get(i), salt));
            item.setUpdateBy(1);
            item.setUpdateTime(LocalDateTime.now());
        }
        accountMapper.updateAccountAndPwd(accounts);
    }
}
