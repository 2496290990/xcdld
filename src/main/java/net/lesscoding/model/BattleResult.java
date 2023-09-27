package net.lesscoding.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.lesscoding.entity.BattleProcess;

import java.util.List;

/**
 * @author eleven
 * @date 2023/9/22 15:52
 * @apiNote 战斗结果
 */
@Data
@NoArgsConstructor
public class BattleResult {
    private Player attacker;

    private Player defender;

    private Player winner;

    private List<BattleProcess> processList;

    public BattleResult(Player attacker, Player defender, List<BattleProcess> processList) {
        this.attacker = attacker;
        this.defender = defender;
        this.processList = processList;
    }

    public Player getWinner() {
        return attacker.getHp() != 0 ? attacker : defender;
    }
}
