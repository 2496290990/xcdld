package net.lesscoding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.NpcDrop;
import net.lesscoding.mapper.NpcDropMapper;
import net.lesscoding.service.NpcDropService;
import org.springframework.stereotype.Service;

/**
 * @author eleven
 * @date 2023/10/31 9:25
 * @apiNote
 */
@Service
public class NpcDropServiceImpl extends ServiceImpl<NpcDropMapper, NpcDrop> implements NpcDropService {
}
