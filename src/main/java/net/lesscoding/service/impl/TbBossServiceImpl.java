package net.lesscoding.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.common.Consts;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.entity.BattleProcess;
import net.lesscoding.entity.TbBoss;
import net.lesscoding.entity.TbBossReward;
import net.lesscoding.enums.RoleTypeEnum;
import net.lesscoding.mapper.AccountPlayerMapper;
import net.lesscoding.mapper.TbBossMapper;
import net.lesscoding.mapper.TbBossRewardMapper;
import net.lesscoding.model.Player;
import net.lesscoding.model.dto.BossDto;
import net.lesscoding.service.TbBossRewardService;
import net.lesscoding.service.TbBossService;
import net.lesscoding.utils.BattleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


/**
* @author Lan
* @time 2023-11-07 14:15:28
*/
@Service
public class TbBossServiceImpl extends ServiceImpl<TbBossMapper, TbBoss> implements TbBossService {


    @Autowired
    private AccountPlayerMapper accountPlayerMapper;

    @Autowired
    private TbBossRewardService tbBossRewardService;

    @Autowired
    @Qualifier("jedisRedisTemplate")
    private RedisTemplate<String,Object> jedisTemplate;

    /**
     * 挑战boss
     * @param dto
     */
    @Override
    public List<BattleProcess> challenge(BossDto dto) {
        Player player = getPlayer(dto.getPlayerId(), RoleTypeEnum.PLAYER);
        Optional.ofNullable(player).orElseThrow(() -> new RuntimeException("玩家获取异常！"));

        Player playerBoss = getPlayer(dto.getPlayerId(),RoleTypeEnum.BOSS);
        Optional.ofNullable(playerBoss).orElseThrow(() -> new RuntimeException("BOSS获取异常！"));

        List<BattleProcess> processList = new ArrayList<>();
        BattleUtil.doBattle(player, playerBoss, processList, Consts.INT_STATE_1);

        List<TbBossReward> bossRewardList = new ArrayList<>();
        bossRewardList.add(new TbBossReward(Consts.INT_STATE_1,Consts.INT_STATE_1));
        // 玩家胜利
        if (player.getHp() > Consts.INT_STATE_0) {
            // 发放奖励
            List<TbBossReward> bossRewards = tbBossRewardService.drawLottery(dto.getBossId(), Consts.INT_STATE_1);
            bossRewardList.addAll(bossRewards);
            StringBuilder builder = new StringBuilder("Boss挑战成功！");
            for (TbBossReward reward : bossRewardList) {
                builder.append(String.format("【摸到了%s * %s】-",reward.getRewardName(),reward.getNumber()));
            }
            processList.add(new BattleProcess(null,builder.toString()));
        }else {
            // 发放安慰奖
            StringBuilder builder = new StringBuilder("Boss挑战失败！");
            for (TbBossReward reward : bossRewardList) {
                builder.append(String.format("【摸到了%s * %s】-",reward.getRewardName(),reward.getNumber()));
            }
            processList.add(new BattleProcess(null,builder.toString()));
            // 结算奖励
            this.settlement(dto.getPlayerId(),bossRewardList);
            return processList;
        }

        // 概率刷新父类boss
        TbBoss boss = super.getById(dto.getBossId());
        boolean boosResult = getPBossResult(boss.getProbability() / 100D);
        if (!boosResult){
            // 结算奖励
            this.settlement(dto.getPlayerId(),bossRewardList);
            return processList;
        }
        processList.add(new BattleProcess(null,"【吵醒了BigBOSS~~~】 哎哈哈，鸡汤来咯！"));
        Player parentPlayerBoss = getPlayer(boss.getParentId(),RoleTypeEnum.BOSS);
        BattleUtil.doBattle(player, parentPlayerBoss, processList, Consts.INT_STATE_1);
        // 发放奖励
        if (player.getHp() > Consts.INT_STATE_0) {
            List<TbBossReward> bossRewards = tbBossRewardService.drawLottery(dto.getBossId(),Consts.INT_STATE_1);
            bossRewardList.addAll(bossRewards);
            StringBuilder builder = new StringBuilder("BigBoss挑战成功！");
            for (TbBossReward reward : bossRewards) {
                builder.append(String.format("【摸到了%s * %s】-",reward.getRewardName(),reward.getNumber()));
            }
            processList.add(new BattleProcess(null,builder.toString()));
            // 结算奖励
            this.settlement(dto.getPlayerId(),bossRewardList);
        }else {
            // 无奖励，扣除体力值
            Integer energy = 200;
            this.deductEnergy(player.getId(),energy);
            processList.add(new BattleProcess(null,String.format("BigBOSS挑战失败！奖品丢失，扣除%s体力虚弱中。。。",energy)));
        }
        return processList;
    }

    /**
     * 结算奖励
     * @param playerId
     * @param bossRewards
     */
    private void settlement(Integer playerId,List<TbBossReward> bossRewards){
        Integer species = Consts.INT_STATE_0;
        for (TbBossReward reward : bossRewards) {
            if (Consts.INT_STATE_1.equals(reward.getType())){
                species += reward.getNumber();
            }
        }
        accountPlayerMapper.addSpecies(playerId,species);
    }
    /**
     * 获取玩家基础属性
     * @param playerId
     * @return
     */
    private Player getPlayer(Integer playerId, RoleTypeEnum roleType) {
        Player queryPlayer = new Player();
        queryPlayer.setId(playerId);
        return roleType == RoleTypeEnum.PLAYER
                ? accountPlayerMapper.getPlayerBaseAttr(queryPlayer)
                : baseMapper.getBossPlayerBaseAttr(playerId);
    }

    /**
     * 刷新父类BOSS概率
     * @param winProbability
     * @return
     */
    private boolean getPBossResult(double winProbability) {
        Random random = new Random();
        double randomNumber = random.nextDouble();
        return randomNumber <= winProbability;
    }

    /**
     * 扣除体力值
     * @param id
     * @param energy
     */
    private void deductEnergy(Integer id, Integer energy) {
        String key = Consts.ENERGY_KEYS + id;
        accountPlayerMapper.subEnergy(id,energy);
        AccountPlayer player = accountPlayerMapper.selectById(id);
        jedisTemplate.opsForValue().set(key, player.getEnergy());
    }


}