package net.lesscoding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.model.Player;

/**
 * @author eleven
 * @date 2023/10/10 17:00
 * @apiNote
 */
public interface AccountPlayerMapper extends BaseMapper<AccountPlayer> {
    Player getPlayerBaseAttr(Integer accountPlayerId);
}
