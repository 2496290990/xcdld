package net.lesscoding.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import net.lesscoding.model.dto.AccountDto;
import net.lesscoding.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eleven
 * @date 2023/10/8 12:13
 * @apiNote 系统控制器
 */
@SaIgnore
@RestController
@RequestMapping("/sys")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @PostMapping("/register")
    public Result registerAccount(@RequestBody AccountDto dto) {
        return ResultFactory.success(systemService.registerAccount(dto));
    }

    @PostMapping("/login")
    public Result doLogin(@RequestBody AccountDto dto) {
        return ResultFactory.success(systemService.doLogin(dto));
    }

}
