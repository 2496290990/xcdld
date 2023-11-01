package net.lesscoding.service.impl;

import java.time.LocalDateTime;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.WeightRandom;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.*;
import net.lesscoding.mapper.*;
import net.lesscoding.model.Player;
import net.lesscoding.model.dto.JoinInstanceDto;
import net.lesscoding.model.dto.NpcFightDto;
import net.lesscoding.service.InstanceNpcService;
import net.lesscoding.utils.BattleUtil;
import net.lesscoding.utils.ExpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eleven
 * @date 2023/10/31 9:43
 * @apiNote
 */
@Service
public class InstanceNpcServiceImpl extends ServiceImpl<InstanceNpcMapper, InstanceNpc> implements InstanceNpcService {
    @Autowired
    private AccountPlayerMapper playerMapper;

    @Autowired
    private InstanceRecordMapper recordMapper;

    @Autowired
    private NpcDropMapper dropMapper;

    @Autowired
    private PlayerPackageMapper packageMapper;

    @Autowired
    private ExpUtil expUtil;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Object challengeNpc(NpcFightDto dto) {
        Player player = getPlayer(dto.getPlayerId());
        InstanceNpc npc = dto.getNpc();
        Player npcPlayer = getPlayer(npc.getPlayerId());
        // 增幅npc
        npcPlayer.increaseNpcAttr(npc);
        List<BattleProcess> processList = new ArrayList<>();
        BattleUtil.doBattle(player, npcPlayer, processList, 1);
        int loginId = StpUtil.getLoginIdAsInt();
        InstanceRecord notCompletedInstance = recordMapper.getNotCompletedInstance(new JoinInstanceDto(player.getId(), npc.getInstanceId()));
        expUtil.addPlayerExp(player, npcPlayer, processList);
        // 玩家胜利
        if (player.getHp() > 0) {
            // Boss
            if (npc.getBossFlag()) {
                notCompletedInstance.setCompleteFlag(true);
                notCompletedInstance.setSuccessFlag(true);
                notCompletedInstance.setCurrentFloor(npc.getFloor());
            } else {
                notCompletedInstance.setCurrentFloor(npc.getFloor() + 1);
            }
            // 随机掉落
            randomNpcDrop(npc, processList, player.getId());
        } else {
            // 此次记录完成
            notCompletedInstance.setCurrentFloor(npc.getFloor());
            notCompletedInstance.setCompleteFlag(true);
            notCompletedInstance.setSuccessFlag(false);
        }
        recordMapper.updateById(notCompletedInstance);
        return processList;
    }

    /**
     * 获取玩家属性
     *
     * @param playerId 玩家id
     * @return Player
     */
    private Player getPlayer(Integer playerId) {
        Player queryPlayer = new Player();
        queryPlayer.setId(playerId);
        return playerMapper.getPlayerBaseAttr(queryPlayer);
    }

    /**
     * 随机npc掉落
     *
     * @param npc      npc
     * @param list     过程
     * @param playerId
     */
    private void randomNpcDrop(InstanceNpc npc, List<BattleProcess> list, Integer playerId) {
        List<String> dropIdList = npc.getDropIdList();
        if (CollUtil.isNotEmpty(dropIdList)) {
            List<NpcDrop> npcDrops = dropMapper.selectList(new QueryWrapper<NpcDrop>()
                    .in("id", dropIdList));
            if (CollUtil.isNotEmpty(npcDrops)) {
                NpcDrop realDrop = null;
                if (npcDrops.size() == 1) {
                    Boolean weightResult = BattleUtil.getWeightResult(npcDrops.get(0).getDropProbability());
                    if (weightResult) {
                        realDrop = npcDrops.get(0);
                    }
                } else {
                    List<WeightRandom.WeightObj<NpcDrop>> weightList = new ArrayList<>(npcDrops.size());
                    for (NpcDrop npcDrop : npcDrops) {
                        weightList.add(new WeightRandom.WeightObj<>(npcDrop, npcDrop.getDropProbability()));
                    }
                    realDrop = RandomUtil.weightRandom(weightList).next();
                }
                if (realDrop != null) {
                    // 类型 0装备 1消耗品 2材料 3掉落礼包
                    PlayerPackage row = packageMapper.selectOne(new QueryWrapper<PlayerPackage>()
                            .eq("type", 3)
                            .eq("player_id", playerId)
                            .eq("obj_id", realDrop.getId()));
                    if (row != null) {
                        row.setNum(row.getNum() + 1);
                        packageMapper.updateById(row);
                    } else {
                        row = new PlayerPackage();
                        row.setPlayerId(playerId);
                        row.setType(3);
                        row.setNum(1);
                        row.setObjId(realDrop.getId());
                    }
                    list.add(new BattleProcess(list.size(),
                            String.format("恭喜获得[%s] * 1, 请前往背包查看", realDrop.getName())));
                }
            }
        }
    }


}
