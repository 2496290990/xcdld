package net.lesscoding.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.entity.Account;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author eleven
 * @date 2023/10/10 16:59
 * @apiNote
 */
@Service
@Slf4j
public class AccountPlayerServiceImpl extends ServiceImpl<AccountPlayerMapper, AccountPlayer> implements AccountPlayerService {

    @Autowired
    private AccountPlayerMapper playerMapper;
    @Autowired
    private PlayerLevelExpMapper levelMapper;

    @Autowired
    @Qualifier("jedisRedisTemplate")
    private RedisTemplate jedisTemplate;
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

    @Override
    public void addPlayerByAccount(List<Account> accountList) {
        List<AccountPlayer> accountPlayerList = new ArrayList<>();
        AccountPlayer accountPlayer = null;
        for (Account account : accountList) {
            accountPlayer = new AccountPlayer();
            accountPlayer.setNickname(account.getNickname());
            accountPlayer.setLevel(0);
            accountPlayer.setExp(0);
            accountPlayer.setAccountId(account.getId());
            accountPlayerList.add(accountPlayer);
        }
        saveBatch(accountPlayerList);
    }

    @Override
    @Scheduled(cron = "0 30 0 * * ?")
    public Integer delRedisEnergy() throws IOException {
        log.info("执行删除体力值key的操作");
        Set<String> energyKeys  = jedisTemplate.keys("energy:*");
        long delNum = jedisTemplate.delete(energyKeys);
        //Boolean energyDelFlag = jedisTemplate.delete("energy:*");
        Integer effectRow = playerMapper.selfRecoveryEnergy();
        log.info("删除数量 {} , 受影响行数 {}\n key值{}", delNum, effectRow, energyKeys);
        return effectRow;
    }

    @Override
    @Scheduled(cron = "0 0 * * * ?")
    public Integer selfRecovery() throws IOException {
        log.info("执行自动删命令开始,有新的战斗重新计算");
        Integer effectRow = delRedisEnergy();
        log.info("执行完成");
        return effectRow;
    }
}
