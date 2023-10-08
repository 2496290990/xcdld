package net.lesscoding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/9/26 16:53
 * @apiNote 武器
 */
@Data
@TableName("tb_weapon")
public class Weapon extends BaseEntity<Weapon> {
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
