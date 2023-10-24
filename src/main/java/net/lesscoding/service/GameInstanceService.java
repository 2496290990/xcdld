package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.GameInstance;

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
     * 查询副本NPC列表
     * @param instance 副本
     * @return List
     */
    List instanceNpcList(GameInstance instance);
}
