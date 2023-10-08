package net.lesscoding.service.impl;

import net.lesscoding.model.dto.AccountDto;
import net.lesscoding.service.AccountService;
import net.lesscoding.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author eleven
 * @date 2023/10/8 12:14
 * @apiNote
 */
@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private AccountService accountService;

    @Override
    public Object registerAccount(AccountDto dto) {
        return accountService.registerAccount(dto);
    }
}
