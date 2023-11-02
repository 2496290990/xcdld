package net.lesscoding.service.impl;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.entity.BattleProcess;
import net.lesscoding.entity.PlayerWeapon;
import net.lesscoding.entity.Weapon;
import net.lesscoding.mapper.AccountPlayerMapper;
import net.lesscoding.mapper.PlayerWeaponMapper;
import net.lesscoding.model.Player;
import net.lesscoding.model.dto.AddExpDto;
import net.lesscoding.model.dto.BattleDto;
import net.lesscoding.model.vo.AfterPlayerVo;
import net.lesscoding.service.AccountPlayerService;
import net.lesscoding.service.BattleService;
import net.lesscoding.utils.BattleUtil;
import net.lesscoding.utils.ExpUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Autowired
    private PlayerWeaponMapper playerWeaponMapper;

    @Autowired
    private ExpUtil expUtil;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Object doBattle(BattleDto dto) {
        Player attacker =  playerMapper.getPlayerBaseAttr(dto.getAttacker());
        Player defender =  playerMapper.getPlayerBaseAttr(dto.getDefender());
        List<BattleProcess> processList = new ArrayList<>();
        BattleUtil.doBattle(attacker, defender, processList, 1);
        processList.add(new BattleProcess(
                processList.size(),
                String.format("【战斗结果】:%s", BattleUtil.getFightResult(attacker, defender))
        ));
        expUtil.addPlayerExp(attacker, defender, processList);
        return processList;
    }

}
