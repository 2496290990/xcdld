package net.lesscoding.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.entity.PlayerLevelExp;
import net.lesscoding.mapper.AccountPlayerMapper;
import net.lesscoding.mapper.PlayerLevelExpMapper;
import net.lesscoding.model.Player;
import net.lesscoding.model.dto.PlayerDto;
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
    @Autowired
    private PlayerLevelExpMapper levelMapper;


    /**
     * 获取所有的玩家数据
     * @return
     */
    @Override
    public Page getAllPlayer(PlayerDto dto) {
        return playerMapper.getAllPlayer(dto.getPage(), dto);
    }

    @Override
    public Object getPlayerDetail(AccountPlayer dto) {
        Integer accountId = dto.getAccountId();
        if (accountId == null) {
            accountId = StpUtil.getLoginIdAsInt();
        }
        PlayerLevelExp maxLevelExp = levelMapper.selectOne(new QueryWrapper<PlayerLevelExp>()
                .lambda()
                .orderByDesc(PlayerLevelExp::getLevel)
                .last("limit 1"));
        // TODO: 2023/10/25  
        return null;
    }
}
