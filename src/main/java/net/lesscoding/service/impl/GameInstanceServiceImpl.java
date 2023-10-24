package net.lesscoding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.GameInstance;
import net.lesscoding.mapper.GameInstanceMapper;
import net.lesscoding.service.GameInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author eleven
 * @date 2023/10/24 8:39
 * @apiNote
 */
@Service
public class GameInstanceServiceImpl extends ServiceImpl<GameInstanceMapper, GameInstance> implements GameInstanceService {
    @Autowired
    private GameInstanceMapper instanceMapper;

    @Override
    public List<GameInstance> getInstanceList() {
        return instanceMapper.selectList(new QueryWrapper<GameInstance>()
                .eq("del_flag", false)
                .orderByAsc("access_level"));
    }

    @Override
    public List instanceNpcList(GameInstance instance) {
        //List list = instanceMapper.getInstanceNpcList(instance);
        return null;
    }
}
