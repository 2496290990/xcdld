package net.lesscoding.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.entity.BattleProcess;
import net.lesscoding.entity.Weapon;
import net.lesscoding.mapper.AccountPlayerMapper;
import net.lesscoding.model.Player;
import net.lesscoding.model.dto.AddExpDto;
import net.lesscoding.model.dto.BattleDto;
import net.lesscoding.service.AccountPlayerService;
import net.lesscoding.service.BattleService;
import net.lesscoding.utils.BattleUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author eleven
 * @date 2023/9/27 12:16
 * @apiNote
 */
@Service
@Slf4j
public class BattleServiceImpl implements BattleService {

    @Autowired
    private AccountPlayerMapper playerMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Object doBattle(BattleDto dto) {
        Player attacker = getLastPlayer(dto.getAttacker());
        Player defender = getLastPlayer(dto.getDefender());
        List<BattleProcess> processList = new ArrayList<>();
        BattleUtil.doBattle(attacker, defender, processList, 1);
        processList.add(new BattleProcess(
                processList.size(),
                String.format("【战斗结果】:%s", BattleUtil.getFightResult(attacker, defender))
        ));
        addPlayerExp(attacker, defender, processList);
        return processList;
    }

    private void addPlayerExp(Player attacker, Player defender, List<BattleProcess> processList) {
        List<Player> playerList = new ArrayList<>();
        Collections.addAll(playerList, attacker, defender);
        playerList.sort(Comparator.comparingInt(Player::getHp));
        Player loser = playerList.get(0);
        Player winner = playerList.get(1);
        // 等级差
        int winnerLv = winner.getLevel();
        int loserLv = loser.getLevel();
        int lvDiff = Math.abs(winnerLv - loserLv);
        int lvExp = lvDiff + 1;
        int hpExpRatio = Math.max(2, lvExp * lvExp);
        int hpExp = 1;
        log.info("当前胜利者 {} 等级 {}", winner.getNickname(), winnerLv);
        log.info("当前失败者 {} 等级 {}", loser.getNickname(), loser);
        log.info("当前胜利者剩余HP {}", winner.getHp());
        log.info("等级差：：{}，hp比例：：{}", lvDiff, hpExpRatio);
        if (winnerLv < loserLv) {
            hpExp = winner.getHp() * hpExpRatio;
            log.info("当前计算公式 {} + {} * {} + 1 = {}",
                    lvExp,
                    winner.getHp(),
                    hpExpRatio,
                    1,
                    hpExp);
        } else {
            hpExp = winner.getHp() / hpExpRatio;
            log.info("当前计算公式 {} + {} / {} + 1 = {}",
                    lvExp,
                    winner.getHp(),
                    hpExpRatio,
                    1,
                    hpExp);
        }

        int winnerExp = lvDiff > 5 ? 0 : lvExp + lvExp + hpExp + 1;
        int loserExp = Math.max(1, winnerExp / 20);
        playerMapper.addPlayerExp(new AddExpDto(winner.getId(), winnerExp));
        playerMapper.addPlayerExp(new AddExpDto(loser.getId(), loserExp));
        processList.add(new BattleProcess(processList.size() + 1,
                String.format("你获得了%d经验值", attacker.getHp() > 0 ? winnerExp : loserExp)));
        AccountPlayer afterAttacker = playerMapper.selectById(attacker.getId());
        if (afterAttacker.getLevel() > attacker.getLevel()) {
            processList.add(new BattleProcess(processList.size() + 2,
                    String.format("你升级了，当前%d级", afterAttacker.getLevel())));
        }
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
