package net.lesscoding.model.dto;

import lombok.Data;
import net.lesscoding.model.Player;

/**
 * @author eleven
 * @date 2023/10/10 17:09
 * @apiNote
 */
@Data
public class BattleDto {
    /**
     * 攻击者
     */
    private Player attacker;

    /**
     * 防御者
     */
    private Player defender;

}
