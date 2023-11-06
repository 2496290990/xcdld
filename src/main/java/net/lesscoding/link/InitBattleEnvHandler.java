package net.lesscoding.link;

import cn.hutool.core.util.RandomUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.lesscoding.entity.Weapon;
import net.lesscoding.model.dto.CurrentBattleProcess;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class InitBattleEnvHandler extends BattleRequestHandler {
    @Override
    public void handleRequest(BattleRequest request) {
        CurrentBattleProcess currentBattleProcess = request.getCurrentBattleProcess();
        StringBuilder currentResult = currentBattleProcess.getCurrentResult();
        List<Weapon> attackerWeaponList = currentBattleProcess.getAttackerWeaponList();

        currentResult.append(String.format("【第%d回合】", currentBattleProcess.getRound()));

        Weapon weapon = RandomUtil.randomEle(attackerWeaponList);
        currentBattleProcess.setCurrentWeapon(weapon);
    }
}
