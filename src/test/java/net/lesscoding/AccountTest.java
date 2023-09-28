package net.lesscoding;

import net.lesscoding.entity.Account;
import net.lesscoding.mapper.AccountMapper;
import net.lesscoding.service.AccountService;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author eleven
 * @date 2023/9/27 16:46
 * @apiNote
 */
@SpringBootTest(classes = MainApp.class)
@RunWith(SpringRunner.class)
public class AccountTest {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountMapper accountMapper;

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
}
