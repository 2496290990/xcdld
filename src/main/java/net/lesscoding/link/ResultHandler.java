package net.lesscoding.link;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.lesscoding.model.dto.CurrentBattleProcess;

@NoArgsConstructor
@Getter
@Setter
public class ResultHandler extends BattleRequestHandler {
    @Override
    public void handleRequest(BattleRequest request) {
        CurrentBattleProcess currentBattleProcess = request.getCurrentBattleProcess();
        calHp(currentBattleProcess);
        setResult(currentBattleProcess);
        setEnd(currentBattleProcess);
    }

    public static void calHp(CurrentBattleProcess currentBattleProcess) {
        Integer calAttack = currentBattleProcess.getCalAttack();
        Integer defenderHp = currentBattleProcess.getDefenderHp();
        int calDefenderHp = Math.max(0, defenderHp - calAttack);
        currentBattleProcess.setDefenderHp(calDefenderHp);
    }

    /**
     * 设置战斗过程文本
     */
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

    /**
     * 设置战斗结束
     */
    public static void setEnd(CurrentBattleProcess currentBattleProcess) {
        Integer attackerHp = currentBattleProcess.getAttackerHp();
        Integer defenderHp = currentBattleProcess.getDefenderHp();
        if (attackerHp > 0 && defenderHp > 0) {
            currentBattleProcess.setCurrentState(0);
        } else if (attackerHp <= 0 & defenderHp <= 0) {
            currentBattleProcess.setCurrentState(3);
        } else if (defenderHp == 0) {
            currentBattleProcess.setCurrentState(1);
        } else {
            currentBattleProcess.setCurrentState(2);
        }
        Integer currentState = currentBattleProcess.getCurrentState();

        StringBuilder currentResult = currentBattleProcess.getCurrentResult();
        switch (currentState) {
            case 1:
                currentResult.append("你击败了【")
                        .append(currentBattleProcess.getDefenderName())
                        .append("】获得了胜利。评价：")
                        .append(attackerHp < 10 ? "惨胜" : "完胜");
                break;
            case 2:
                currentResult.append("惜败！");
                break;
            case 3:
                currentResult.append("平局");
                break;
            default:
                break;
        }
    }
}
