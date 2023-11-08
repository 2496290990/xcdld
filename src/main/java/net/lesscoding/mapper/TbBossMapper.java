package net.lesscoding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lesscoding.entity.TbBoss;
import net.lesscoding.model.Player;


/**
* @author Lan
* @time 2023-11-07 14:15:28
*/
public interface TbBossMapper extends BaseMapper<TbBoss>{

    Player getBossPlayerBaseAttr(Integer bossId);
}
