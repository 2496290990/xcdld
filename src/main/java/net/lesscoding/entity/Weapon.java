package net.lesscoding.entity;

import lombok.Data;

/**
 * @author eleven
 * @date 2023/9/26 16:53
 * @apiNote 武器
 */
@Data
public class Weapon {
    /**
     * 名称
     */
    private String name;

    /**
     * 最小攻击
     */
    private Integer minDamage;

    private Integer maxDamage;

    private Integer level;

    private String description;

    private Integer star;

    public Weapon(String name) {
        this.name = name;
        this.minDamage = 3;
        this.maxDamage = 7;
        this.level = 0;
        this.description = "测试";
        this.star = 0;
    }
}
