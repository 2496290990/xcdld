package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.TbBossReward;

import java.util.List;


/**
* @author Lan
* @time 2023-11-07 14:15:52
*/
public interface TbBossRewardService extends IService<TbBossReward> {
    /**
     * boss奖品抽奖
     * @param bossId
     * @param round 次数
     * @return
     */
    List<TbBossReward> drawLottery(Integer bossId, Integer round);
}