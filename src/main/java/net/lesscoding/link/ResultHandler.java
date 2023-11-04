package net.lesscoding.link;

import lombok.*;
import net.lesscoding.model.dto.CurrentBattleProcess;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResultHandler extends BattleRequestHandler {
    @Override
    public void handleRequest(BattleRequest request) {
        CurrentBattleProcess currentBattleProcess = request.getCurrentBattleProcess();
        calHp(currentBattleProcess);
        setResult(currentBattleProcess);
    }

    public static void calHp(CurrentBattleProcess currentBattleProcess) {
        Integer calAttack = currentBattleProcess.getCalAttack();
        Integer defenderHp = currentBattleProcess.getDefenderHp();
        int calDefenderHp = Math.max(0, defenderHp - calAttack);
        currentBattleProcess.setDefenderHp(calDefenderHp);
    }

    public static void setResult(CurrentBattleProcess currentBattleProcess) {
        Boolean fleeFlag = currentBattleProcess.getFleeFlag();
        Boolean criticalFlag = currentBattleProcess.getCriticalFlag();
        boolean useWeaponFlag = currentBattleProcess.getCurrentWeapon() != null;
        StringBuilder currentResult = currentBattleProcess.getCurrentResult();
        if (useWeaponFlag) {
            currentResult.append(String.format("玩家[%s]使用了武器[%s],",
                    currentBattleProcess.getAttackerName(), currentBattleProcess.getCurrentWeapon().getName()));
            currentResult.append(String.format("攻击力提升了%d,当前%d,",
                    currentBattleProcess.getCalAttack() - currentBattleProcess.getAttackerAttack(), currentBattleProcess.getCalAttack()));
        }

        if (fleeFlag) {
            String baseText = "一击";
            if (criticalFlag) {
                baseText = "致命一击";
            }
            currentResult.append(String.format("%s侧身一躲，躲过了%s的",
                    currentBattleProcess.getDefenderName(),
                    currentBattleProcess.getAttackerName()))
                    .append(baseText);
        } else {
            if (criticalFlag) {
                currentResult.append("[触发暴击，基础伤害 * 2]:");
            }
            currentResult.append(String.format("%s 被击中, 造成 %d, 当前HP: %d",
                    currentBattleProcess.getDefenderName(),
                    currentBattleProcess.getCalAttack(),
                    currentBattleProcess.getDefenderHp()));
        }
    }
}
