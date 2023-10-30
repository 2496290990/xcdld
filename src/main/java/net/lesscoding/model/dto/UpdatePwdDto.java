package net.lesscoding.model.dto;

import lombok.Data;

/**
 * @author eleven
 * @date 2023/10/30 20:52
 * @apiNote
 */
@Data
public class UpdatePwdDto {
    private String mac;

    private String oldPwd;

    private String newAccount;

    private String newPwd;
}
