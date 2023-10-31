package net.lesscoding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lesscoding.entity.InstanceRecord;
import net.lesscoding.model.dto.JoinInstanceDto;

/**
 * @author eleven
 * @date 2023/10/31 9:16
 * @apiNote
 */
public interface InstanceRecordMapper extends BaseMapper<InstanceRecord> {

    /**
     * 获取当日未完成的副本记录
     * @param loginId 登录者id
     * @return
     */
    InstanceRecord getNotCompletedInstance(JoinInstanceDto loginId);
}
