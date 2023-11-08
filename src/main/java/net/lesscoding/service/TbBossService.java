package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.TbBoss;
import net.lesscoding.model.dto.BossDto;


/**
* @author Lan
* @time 2023-11-07 14:15:28
*/
public interface TbBossService extends IService<TbBoss> {
    /**
     * 挑战boss
     * @param bossDto
     */
    public void challenge(BossDto bossDto);
}