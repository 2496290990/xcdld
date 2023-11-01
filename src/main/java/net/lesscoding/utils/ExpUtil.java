package net.lesscoding.utils;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.entity.BattleProcess;
import net.lesscoding.entity.PlayerWeapon;
import net.lesscoding.entity.Weapon;
import net.lesscoding.mapper.AccountPlayerMapper;
import net.lesscoding.mapper.PlayerWeaponMapper;
import net.lesscoding.model.Player;
import net.lesscoding.model.dto.AddExpDto;
import net.lesscoding.model.vo.AfterPlayerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author eleven
 * @date 2023/10/31 11:22
 * @apiNote
 */
@Component
@Slf4j
public class ExpUtil {

    @Autowired
    private AccountPlayerMapper playerMapper;

    @Autowired
    private PlayerWeaponMapper playerWeaponMapper;

    /**
     * 添加玩家的经验
     * @param attacker          攻击者
     * @param defender          防御者
     * @param processList       进程集合
     */
    public void addPlayerExp(Player attacker, Player defender, List<BattleProcess> processList) {
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
        //int hpExpRatio = Math.max(2, lvExp * lvExp);
        int hpExpRatio = Math.max(2, lvExp * lvExp);
        int hpExp = 1;
        log.info("当前胜利者 {} 等级 {}", winner.getNickname(), winnerLv);
        log.info("当前失败者 {} 等级 {}", loser.getNickname(), loserLv);
        log.info("当前胜利者剩余HP {}", winner.getHp());
        log.info("等级差：：{}，hp比例：：{}", lvDiff, hpExpRatio);
        int realLvExp = lvDiff > 5 ? 1 : lvExp;
        log.info("等级差经验为 {}, 公式： 等级差 > 5 ? 1 : 等级差 + 1 ", realLvExp);
        String formatStr = "当前计算公式 %d + %d %s %d + 1 = %d";
        String hpExpLogStr = "";
        boolean vsHighLevel = winnerLv < loserLv;
        if (vsHighLevel) {
            //hpExp = winner.getHp() * hpExpRatio;
            hpExp = winner.getHp() * Math.max(2, lvExp);
        } else {
            hpExp = winner.getHp() / hpExpRatio;
        }
        hpExpLogStr = String.format(formatStr,
                realLvExp,
                winner.getHp(), vsHighLevel ? "*" : "/" , hpExpRatio,
                hpExp);
        log.info(hpExpLogStr);
        //int winnerExp = (lvDiff > 5 ? 0 : lvExp)+ lvExp + hpExp + 1;
        int winnerExp = realLvExp + hpExp + 1;
        //int loserExp = Math.max(lvDiff / 2, winnerExp / (5 + lvDiff));
        int loserExp = Math.max(lvDiff * lvDiff, winnerExp / 5  / lvExp);
        log.info("胜利者获得 {} exp， 失败者获得 {} exp", winnerExp, loserExp);
        playerMapper.addPlayerExp(new AddExpDto(winner.getId(), winnerExp, winner.getIsNpc()));
        playerMapper.addPlayerExp(new AddExpDto(loser.getId(), loserExp, loser.getIsNpc()));
        processList.add(new BattleProcess(processList.size() + 1,
                String.format("你获得了%d经验值", attacker.getHp() > 0 ? winnerExp : loserExp)));
        List<AfterPlayerVo> afterList =  playerMapper.selectAfterPlayer(Arrays.asList(attacker.getId(), defender.getId()));
        addWeapon(attacker, defender, processList, afterList);
    }

    /**
     * 添加获取武器的代码
     * @param attacker          攻击者
     * @param defender          防御者
     * @param processList       进程列表
     * @param afterList         变更之后的玩家列表
     */
    private void addWeapon(Player attacker, Player defender,
                           List<BattleProcess> processList, List<AfterPlayerVo> afterList) {
        Map<Integer, AfterPlayerVo> playerVoMap = afterList.stream()
                .collect(Collectors.toMap(AfterPlayerVo::getId, Function.identity()));
        List<PlayerWeapon> insertList = new ArrayList<>(2);
        addPlayerWeapon(playerVoMap, attacker, insertList, processList, true);
        if (!defender.getIsNpc()) {
            addPlayerWeapon(playerVoMap, defender, insertList, processList, false);
        }
        if (CollUtil.isNotEmpty(insertList)) {
            playerWeaponMapper.insertBatch(insertList);
        }
    }

    /**
     * 添加武器
     * @param playerVoMap
     * @param player
     * @param insertList
     * @param processList
     * @param addFlag
     */
    private void addPlayerWeapon(Map<Integer, AfterPlayerVo> playerVoMap, Player player,
                                 List<PlayerWeapon> insertList, List<BattleProcess> processList,
                                 Boolean addFlag) {
        AfterPlayerVo afterAttacker = playerVoMap.get(player.getId());
        List<PlayerWeapon> playerWeaponList = new ArrayList<>(2);
        if (afterAttacker.getLevel() > player.getLevel()) {
            if (addFlag) {
                processList.add(new BattleProcess(processList.size() + 2,
                        String.format("你升级了，当前%d级", afterAttacker.getLevel())));
            }
            Weapon weapon = afterAttacker.getWeapon();
            if (weapon != null) {
                log.info("当前玩家 {} 获得武器 {}", player, weapon);
                if (weapon.getId() != null) {
                    insertList.add(new PlayerWeapon(player.getId(), weapon.getId(), 0));
                }
                playerWeaponList.add(new PlayerWeapon(player.getId(), weapon.getId(), 0));
                if (addFlag) {
                    processList.add(new BattleProcess(processList.size() + 3,
                            String.format("你获得了【%s】描述【%s】攻击力[%d ~ %d]",
                                    weapon.getName(),
                                    weapon.getIntro(),
                                    weapon.getMinDamage(),
                                    weapon.getMaxDamage()))
                    );
                }
            }
        }
    }
}
