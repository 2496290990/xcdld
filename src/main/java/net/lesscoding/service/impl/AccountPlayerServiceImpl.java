package net.lesscoding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.mapper.AccountPlayerMapper;
import net.lesscoding.model.Player;
import net.lesscoding.service.AccountPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author eleven
 * @date 2023/10/10 16:59
 * @apiNote
 */
@Service
public class AccountPlayerServiceImpl extends ServiceImpl<AccountPlayerMapper, AccountPlayer> implements AccountPlayerService {

    @Autowired
    private AccountPlayerMapper playerMapper;
    @Override
    public Player getPlayerBaseAttr(Integer accountPlayerId) {
        Player player = playerMapper.getPlayerBaseAttr(accountPlayerId);
        return player;
    }
}
