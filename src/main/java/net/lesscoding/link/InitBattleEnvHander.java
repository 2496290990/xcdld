package net.lesscoding.link;

import cn.hutool.core.util.RandomUtil;
import lombok.*;
import net.lesscoding.entity.Weapon;
import net.lesscoding.model.dto.CurrentBattleProcess;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class InitBattleEnvHander extends BattleRequestHandler{
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
