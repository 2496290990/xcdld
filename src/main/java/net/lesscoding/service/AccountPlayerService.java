package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.model.Player;
import net.lesscoding.model.dto.PlayerDto;

/**
 * @author eleven
 * @date 2023/10/10 16:59
 * @apiNote
 */
public interface AccountPlayerService extends IService<AccountPlayer> {

    Page getAllPlayer(PlayerDto dto);

    /**
     * 获取玩家基本信息
     * @param dto
     * @return
     */
    Object getPlayerDetail(AccountPlayer dto);
}
