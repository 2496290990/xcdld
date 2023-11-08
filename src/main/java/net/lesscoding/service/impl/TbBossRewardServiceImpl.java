package net.lesscoding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import com.google.common.collect.Lists;
import net.lesscoding.common.Consts;
import net.lesscoding.entity.TbBossReward;
import net.lesscoding.mapper.TbBossRewardMapper;
import net.lesscoding.service.TbBossRewardService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
* @author Lan
* @time 2023-11-07 14:15:52
*/
@Service
public class TbBossRewardServiceImpl extends ServiceImpl<TbBossRewardMapper, TbBossReward> implements TbBossRewardService {
    /**
     * boss奖品抽奖
     * @param bossId
     * @param round
     * @return
     */
    @Override
    public List<TbBossReward> drawLottery(Integer bossId, Integer round) {
        LambdaQueryWrapper<TbBossReward> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TbBossReward::getBossId, bossId);
        List<TbBossReward> rewards = baseMapper.selectList(wrapper);
        return IntStream.range(Consts.INT_STATE_0, round)
                .mapToObj(i -> draw(rewards))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public TbBossReward draw(List<TbBossReward> rewards) {
        double totalProbability = 0;
        for (TbBossReward reward : rewards) {
            totalProbability += reward.getProbability() / 100D;
        }
        double randomValue = new Random().nextDouble() * totalProbability;
        double accumulatedProbability = 0;
        for (TbBossReward reward : rewards) {
            accumulatedProbability += reward.getProbability();
            if (randomValue <= accumulatedProbability) {
                return reward;
            }
        }
        return null;
    }

}