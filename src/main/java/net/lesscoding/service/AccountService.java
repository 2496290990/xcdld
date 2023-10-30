package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.Account;
import net.lesscoding.model.dto.AccountDto;
import net.lesscoding.model.dto.UpdatePwdDto;

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
    List<Account> getAccountList();

    String registerAccount(Account account);

    Object registerAccount(AccountDto dto);

    /**
     * 登录
     * @param dto
     * @return
     */
    Object doLogin(AccountDto dto);

    /**
     * 并更用户名和密码
     *
     * @param dto 账号
     * @return 密码
     */
    Integer updateAccountPwd(UpdatePwdDto dto);
}
