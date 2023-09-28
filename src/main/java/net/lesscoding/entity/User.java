package net.lesscoding.entity;

import lombok.Data;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/9/22 15:20
 * @apiNote
 */
@Data
public class User extends BaseEntity<User> {
    /**
     * 网卡地址
     */
    private String mac;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 等级
     */
    private Integer level;

    private Integer exp;

    /**
     * 力量
     */
    private Integer power;

    /**
     * 敏捷
     */
    private Integer agility;

    /**
     * 防御
     */
    private Integer defense;

    /**
     * 是否NPC
     */
    private Boolean isNpc;
}
