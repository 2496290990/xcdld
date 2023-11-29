package net.lesscoding.service;

import net.lesscoding.model.dto.BattleDto;
import net.lesscoding.model.dto.TauntDto;

/**
 * @author eleven
 * @date 2023/9/27 12:16
 * @apiNote
 */
public interface BattleService {
    Object doBattle(BattleDto dto);

    /**
     * 嘲讽
     * @param dto
     * @return
     */
    String tauntRavil(TauntDto dto) throws InterruptedException;
}
