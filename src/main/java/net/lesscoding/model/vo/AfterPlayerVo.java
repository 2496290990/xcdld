package net.lesscoding.model.vo;

import lombok.Data;
import net.lesscoding.entity.Weapon;

/**
 * @author eleven
 * @date 2023/10/23 9:16
 * @apiNote
 */
@Data
public class AfterPlayerVo {
    private Integer id;

    private Integer getWeapon;

    private Integer level;

    private Weapon weapon;
}
