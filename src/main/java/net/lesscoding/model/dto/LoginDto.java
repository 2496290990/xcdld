package net.lesscoding.model.dto;

import lombok.Data;

/**
 * @author eleven
 * @date 2023/9/28 15:17
 * @apiNote
 */
@Data
public class LoginDto {
    /**
     * 登录类型
     * 0 使用网卡mac地址登录
     * 1 使用 account 系统生成账号登录
     * 3 使用邮箱验证码登录
     */
    private Integer type;
    private String mac;

    private String account;

    private String email;

    private String password;

}
