package net.lesscoding.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.common.Consts;
import net.lesscoding.entity.Account;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.entity.PlayerLevelExp;
import net.lesscoding.entity.SysDict;
import net.lesscoding.mapper.AccountPlayerMapper;
import net.lesscoding.mapper.PlayerLevelExpMapper;
import net.lesscoding.mapper.SysDictMapper;
import net.lesscoding.model.dto.PlayerDto;
import net.lesscoding.model.vo.PlayerInfoVo;
import net.lesscoding.model.vo.PlayerVo;
import net.lesscoding.service.AccountPlayerService;
import net.lesscoding.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

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

    @Autowired
    private SysDictMapper dictMapper;

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
        playerInfoVo.setMaxEnergy(maxEnergy());
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
        Set<String> energyKeys  = jedisTemplate.keys(Consts.ENERGY_KEYS);
        long delNum = jedisTemplate.delete(energyKeys);
        playerMapper.selfRecoveryEnergy(maxEnergy());
        log.info("删除数量 {} , 受影响行数 {}\n key值{}", delNum, energyKeys.size(), energyKeys);
        return energyKeys.size();
    }

    @Override
    @Scheduled(cron = "0 0/5 * * * ?")
    public Integer selfRecovery() throws IOException {
        log.info("执行每五分钟自动恢复");
        Set<String> energyKeys  = jedisTemplate.keys(Consts.ENERGY_KEYS);
        AccountPlayer accountPlayer = null;
        if (CollUtil.isNotEmpty(energyKeys)) {
            Map<Integer, Integer> playerMap = new HashMap<>(energyKeys.size());
            Integer maxEnergy = maxEnergy();
            for (String energyKey : energyKeys) {
                String energyStr = String.valueOf(jedisTemplate.opsForValue().get(energyKey));
                if (StrUtil.isNotBlank(energyStr) || StrUtil.equalsIgnoreCase("null", energyStr)) {
                    Integer energy = Integer.parseInt(energyStr);
                    energy = Math.min(maxEnergy, energy + (maxEnergy / 12));
                    jedisTemplate.opsForValue().set(energyKey, energy.toString());
                    log.info("当前玩家{} 恢复后体力值为{}", energyKey, energy);
                    playerMap.put(Integer.parseInt(energyKey.split(":")[1]), energy);
                }
            }
            if (CollUtil.isNotEmpty(playerMap)) {
                List<AccountPlayer> updateList = playerMapper.selectList(new QueryWrapper<AccountPlayer>()
                        .in("id", playerMap.keySet()));
                updateList.forEach(item -> item.setEnergy(playerMap.get(item.getId())));
                playerMapper.updateEnergy(updateList);
            }
        }
        log.info("执行完成");
        return energyKeys.size();
    }

    private Integer maxEnergy () {
        SysDict dict = dictMapper.selectOne(new QueryWrapper<SysDict>()
                .lambda()
                .eq(SysDict::getDictType, "energy")
                .eq(SysDict::getDictCode, "maxEnergy"));
        return Integer.parseInt(dict.getDictValue());
    }
}
