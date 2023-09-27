package net.lesscoding.entity;

import lombok.Data;

/**
 * @author eleven
 * @date 2023/9/22 15:52
 * @apiNote 战斗结果
 */
@Data
public class BattleResult {
    /**
     * 攻击者
     */
    private String attacker;

    /**
     * 防御者
     */
    private String defender;

    /**
     * 被攻击的是否是NPC
     */
    private Boolean defenderIsNpc;
}
