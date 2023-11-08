package net.lesscoding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.common.Consts;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.entity.BattleProcess;
import net.lesscoding.entity.TbBoss;
import net.lesscoding.enums.RoleType;
import net.lesscoding.enums.RoleTypeEnum;
import net.lesscoding.mapper.AccountPlayerMapper;
import net.lesscoding.mapper.TbBossMapper;
import net.lesscoding.model.Player;
import net.lesscoding.model.dto.BossDto;
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
    @Qualifier("jedisRedisTemplate")
    private RedisTemplate<String,Object> jedisTemplate;

    /**
     * 挑战boss
     * @param dto
     */
    @Override
    public void challenge(BossDto dto) {
        Player player = getPlayer(dto.getPlayerId(), RoleTypeEnum.PLAYER);
        Optional.ofNullable(player).orElseThrow(() -> new RuntimeException("玩家获取异常！"));

        Player playerBoss = getPlayer(dto.getPlayerId(),RoleTypeEnum.BOSS);
        Optional.ofNullable(playerBoss).orElseThrow(() -> new RuntimeException("BOSS获取异常！"));

        List<BattleProcess> processList = new ArrayList<>();
        BattleUtil.doBattle(player, playerBoss, processList, Consts.INT_STATE_1);
        // 玩家胜利
        if (player.getHp() > Consts.INT_STATE_0) {
            //  发放金币奖励
            accountPlayerMapper.addSpecies(player.getId(),Consts.INT_STATE_2);
        }else {
            // 发放少量金币奖励
            accountPlayerMapper.addSpecies(player.getId(),Consts.INT_STATE_10);
        }
        // 父类boss
        TbBoss boss = super.getById(dto.getBossId());
        Integer probability = boss.getProbability();
        Integer parentId = boss.getParentId();
        boolean boosResult = getPBossResult(probability / 100D);
        if (!boosResult){
            return;
        }
        Integer index = processList.get(processList.size() - Consts.INT_STATE_1).getIndex();
        processList.add(new BattleProcess(null,"吵醒了Big BOSS~~~ 哎哈哈，鸡汤来咯！"));
        Player parentPlayerBoss = getPlayer(parentId,RoleTypeEnum.BOSS);
        BattleUtil.doBattle(player, parentPlayerBoss, processList, index);
        if (player.getHp() > Consts.INT_STATE_0) {
            // 发放大量金币奖励
            accountPlayerMapper.addSpecies(player.getId(),Consts.INT_STATE_10);
        }else {
            // 无奖励，扣除体力值
            this.deductEnergy(player.getId(),200);
        }
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
    public static boolean getPBossResult(double winProbability) {
        Random random = new Random();
        double randomNumber = random.nextDouble();
        return randomNumber <= winProbability;
    }

    /**
     * 扣除体力值
     * @param id
     * @param energy
     */

    public void deductEnergy(Integer id, Integer energy) {
        String key = Consts.ENERGY_KEYS + id;
        accountPlayerMapper.subEnergy(id,energy);
        AccountPlayer player = accountPlayerMapper.selectById(id);
        jedisTemplate.opsForValue().set(key, player.getEnergy());
    }


}