package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.model.Player;

/**
 * @author eleven
 * @date 2023/10/10 16:59
 * @apiNote
 */
public interface AccountPlayerService extends IService<AccountPlayer> {
    /**
     * 获取游戏角色的基本熟悉
     * @param accountPlayerId       角色id
     * @return
     */
    Player getPlayerBaseAttr(Integer accountPlayerId);
}
