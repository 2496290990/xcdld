package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.Account;

import java.util.List;

/**
 * @author eleven
 * @date 2023/9/27 15:53
 * @apiNote
 */
public interface AccountService extends IService<Account> {

    /**
     * 获取所有的用户数据
     * @return
     */
    public List<Account> getAccountList();

    String registerAccount(Account account);
}
