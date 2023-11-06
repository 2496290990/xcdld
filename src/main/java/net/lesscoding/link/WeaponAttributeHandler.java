package net.lesscoding.link;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.lesscoding.entity.Weapon;
import net.lesscoding.model.dto.CurrentBattleProcess;
import net.lesscoding.utils.BattleUtil;

@NoArgsConstructor
@Getter
@Setter
public class WeaponAttributeHandler extends BattleRequestHandler {
    @Override
    public void handleRequest(BattleRequest request) {
        Boolean useWeaponFlag = BattleUtil.getWeightResult(0.5);
        CurrentBattleProcess currentBattleProcess = request.getCurrentBattleProcess();
        int attack = calAttack(useWeaponFlag, currentBattleProcess);
        currentBattleProcess.setCalAttack(attack);
    }

    public static int calAttack(Boolean useWeaponFlag, CurrentBattleProcess currentBattleProcess) {
        int baseAttack = 0;
        int extraAttck = 0;
        if (useWeaponFlag) {
            extraAttck = calWeaponBuff(currentBattleProcess);
        } else {
            currentBattleProcess.setCurrentWeapon(null);
        }
        baseAttack = commonCalAttack(useWeaponFlag, currentBattleProcess) + extraAttck;
        return baseAttack;
    }

    public static int commonCalAttack(Boolean useWeaponFlag, CurrentBattleProcess currentBattleProcess) {
        Boolean fleeFlag = currentBattleProcess.getFleeFlag();
        int attack = 0;
        if (fleeFlag) {
            return attack;
        }
        attack = currentBattleProcess.getAttackerAttack();
        if (useWeaponFlag) {
            Weapon weapon = currentBattleProcess.getCurrentWeapon();
            attack = attack + BattleUtil.getRangeAttack(weapon.getMinDamage(), weapon.getMaxDamage());
        }
        attack = Math.max(0, attack - currentBattleProcess.getDefenderDefence());
        Boolean critical = currentBattleProcess.getCriticalFlag();
        if (critical) {
            attack *= 2;
        }
        return attack;
    }

    public static int calWeaponBuff(CurrentBattleProcess currentBattleProcess) {
        return 0;
    }
}
