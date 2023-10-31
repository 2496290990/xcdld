package net.lesscoding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lesscoding.entity.InstanceNpc;
import net.lesscoding.model.dto.JoinInstanceDto;
import net.lesscoding.model.vo.NpcPlayerVo;

import java.util.List;

/**
 * @author eleven
 * @date 2023/10/31 9:42
 * @apiNote
 */
public interface InstanceNpcMapper extends BaseMapper<InstanceNpc> {
    /**
     * 获取副本npc列表
     * @param dto 查询参数
     * @return
     */
    List<InstanceNpc> selectInstanceNpcList(JoinInstanceDto dto);

    /**
     * 获取npc数据
     * @param npcId
     * @return
     */
    NpcPlayerVo getNpcAttr(Integer npcId);
}
