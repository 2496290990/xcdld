package net.lesscoding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import net.lesscoding.entity.SysChangeLog;
import net.lesscoding.mapper.SysChangeLogMapper;
import net.lesscoding.service.SysChangeLogService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @author Lan
* @time 2023-11-07 17:30:49
*/
@Service
public class SysChangeLogServiceImpl extends ServiceImpl<SysChangeLogMapper, SysChangeLog> implements SysChangeLogService {

}