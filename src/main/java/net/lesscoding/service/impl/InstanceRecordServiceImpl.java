package net.lesscoding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.InstanceRecord;
import net.lesscoding.mapper.InstanceRecordMapper;
import net.lesscoding.service.InstanceRecordService;
import org.springframework.stereotype.Service;

/**
 * @author eleven
 * @date 2023/10/31 9:24
 * @apiNote
 */
@Service
public class InstanceRecordServiceImpl extends ServiceImpl<InstanceRecordMapper, InstanceRecord> implements InstanceRecordService {
}
