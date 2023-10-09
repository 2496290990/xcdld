package net.lesscoding;

import net.lesscoding.entity.Account;
import net.lesscoding.mapper.AccountMapper;
import net.lesscoding.service.AccountService;
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
import java.util.Map;

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

        //Map userNameMap = hashRedisTemplate.opsForHash().entries(userNameCache);
        //userNameMap.forEach((key, value) -> {
        //    System.out.println(String.format("key: %s\n value:%s", key, value));
        //});
        //hashRedisTemplate.execute(new RedisCallback() {
        //    @Override
        //    public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
        //
        //        Map<byte[], byte[]> map = redisConnection.hGetAll(userNameCache.getBytes());
        //        map.forEach((key, value) -> {
        //            System.out.println(String.format("key: %s, value: %s", new String(key), new String(value)));
        //        });
        //        return null;
        //    }
        //});
    }
}
