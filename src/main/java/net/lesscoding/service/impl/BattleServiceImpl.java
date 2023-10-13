package net.lesscoding.service.impl;

import net.lesscoding.entity.BattleProcess;
import net.lesscoding.entity.Weapon;
import net.lesscoding.mapper.AccountPlayerMapper;
import net.lesscoding.model.Player;
import net.lesscoding.model.dto.BattleDto;
import net.lesscoding.service.AccountPlayerService;
import net.lesscoding.service.BattleService;
import net.lesscoding.utils.BattleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author eleven
 * @date 2023/9/27 12:16
 * @apiNote
 */
@Service
public class BattleServiceImpl implements BattleService {

    @Autowired
    private AccountPlayerMapper playerMapper;
    @Override
    public Object doBattle(BattleDto dto) {
        Player attacker = getLastPlayer(dto.getAttacker());
        Player defender = getLastPlayer(dto.getDefender());
        List<BattleProcess> processList = new ArrayList<>();
        BattleUtil.doBattle(attacker, defender, processList, 1);
        return processList;
    }

    private Player getLastPlayer(Player player) {
        Weapon basketball = new Weapon("蓝球");
        Weapon keyboard = new Weapon("键盘");
        List<Weapon> weapons = Arrays.asList(basketball, keyboard);
        // 获取角色的基本属性
        player = playerMapper.getPlayerBaseAttr(player);
        player.setWeaponList(weapons);
        return player;
    }
}
