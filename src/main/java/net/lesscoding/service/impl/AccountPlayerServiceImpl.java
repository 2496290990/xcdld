package net.lesscoding.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.entity.PlayerLevelExp;
import net.lesscoding.mapper.AccountPlayerMapper;
import net.lesscoding.mapper.PlayerLevelExpMapper;
import net.lesscoding.model.Player;
import net.lesscoding.model.dto.PlayerDto;
import net.lesscoding.model.vo.PlayerInfoVo;
import net.lesscoding.model.vo.PlayerVo;
import net.lesscoding.service.AccountPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Page<PlayerVo> allPlayer = playerMapper.getAllPlayer(dto.getPage(), dto);
        allPlayer.getRecords().forEach(item -> {
            item.setOnline(StrUtil.isNotBlank(StpUtil.getTokenValueByLoginId(item.getId())));
        });
        return allPlayer;
    }

    @Override
    public PlayerInfoVo getPlayerDetail(AccountPlayer dto) {
        Integer accountId = dto.getAccountId();
        if (accountId == null) {
            accountId = StpUtil.getLoginIdAsInt();
        }
        PlayerLevelExp maxLevel = levelMapper.selectOne(new QueryWrapper<PlayerLevelExp>()
                .lambda()
                .orderByDesc(PlayerLevelExp::getLevel)
                .last("limit 1"));
        PlayerInfoVo playerInfoVo = playerMapper.selectPlayerDetail(accountId);
        if (playerInfoVo.getNextLvExp() == null) {
            playerInfoVo.setNextLvExp(maxLevel.getNeedExp());
            playerInfoVo.setHitRate(maxLevel.getHit());
            playerInfoVo.setComboRate(maxLevel.getCombo());
            playerInfoVo.setFlee(maxLevel.getFlee());
            playerInfoVo.setHp(maxLevel.getHp());
            playerInfoVo.setDefence(maxLevel.getDefender());
            playerInfoVo.setAttack(maxLevel.getAttack());
        }
        return playerInfoVo;
    }
}
