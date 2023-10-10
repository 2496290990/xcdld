package net.lesscoding.service;

import net.lesscoding.model.dto.BattleDto;

/**
 * @author eleven
 * @date 2023/9/27 12:16
 * @apiNote
 */
public interface BattleService {
    Object doBattle(BattleDto dto);
}
