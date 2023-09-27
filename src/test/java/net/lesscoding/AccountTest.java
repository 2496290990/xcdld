package net.lesscoding;

import net.lesscoding.entity.Account;
import net.lesscoding.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void randomAccount() {
        int cycle = 50;
        for (int i = 0; i < cycle; i++) {
            accountService.registerAccount(new Account());
        }
    }
}
