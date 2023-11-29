package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.InstanceNpc;
import net.lesscoding.model.dto.NpcFightDto;
import net.lesscoding.model.vo.fight.BattleResult;

/**
 * @author eleven
 * @date 2023/10/31 9:43
 * @apiNote
 */
public interface InstanceNpcService extends IService<InstanceNpc> {
    /**
     * 挑战npc
     *
     * @param dto 战斗类
     * @return BattleResult
     */
    BattleResult challengeNpc(NpcFightDto dto);
}
