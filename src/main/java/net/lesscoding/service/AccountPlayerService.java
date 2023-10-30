package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.Account;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.model.dto.PlayerDto;
import net.lesscoding.model.vo.PlayerInfoVo;

import java.util.List;

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
    PlayerInfoVo getPlayerDetail(AccountPlayer dto);

    /**
     * 根据账号添加角色
     *
     * @param accountList
     */
    void addPlayerByAccount(List<Account> accountList);
}
