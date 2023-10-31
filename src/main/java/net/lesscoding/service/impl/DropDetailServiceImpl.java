package net.lesscoding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.DropDetail;
import net.lesscoding.mapper.DropDetailMapper;
import net.lesscoding.service.DropDetailService;
import org.springframework.stereotype.Service;

/**
 * @author eleven
 * @date 2023/10/31 9:26
 * @apiNote
 */
@Service
public class DropDetailServiceImpl extends ServiceImpl<DropDetailMapper, DropDetail> implements DropDetailService {
}
