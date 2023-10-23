package net.lesscoding.model.vo;

import lombok.Data;

/**
 * @author eleven
 * @date 2023/10/12 22:05
 * @apiNote
 */
@Data
public class PlayerVo{
    /**
     * 角色id
     */
    private Integer id;

    /**
     * 账号id
     */
    private Integer accountId;

    /**
     * 名称
     */
    private String nickname;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 省份
     */
    private String region;

    /**
     * 网卡地址
     */
    private String mac;

    private Integer exp;

    private Integer needExp;

    private Integer energy;

}
