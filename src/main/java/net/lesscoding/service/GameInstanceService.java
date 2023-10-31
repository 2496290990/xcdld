package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.GameInstance;
import net.lesscoding.model.dto.JoinInstanceDto;
import net.lesscoding.model.vo.ChallengeInstanceVo;

import java.util.List;

/**
 * @author eleven
 * @date 2023/10/24 8:38
 * @apiNote
 */
public interface GameInstanceService extends IService<GameInstance> {
    /**
     * 获取副本列表
     * @return List
     */
    List<GameInstance> getInstanceList();

    /**
     * 加入副本
     * @param dto
     * @return
     */
    ChallengeInstanceVo joinInstance(JoinInstanceDto dto);
}
