package net.lesscoding.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.entity.Account;
import net.lesscoding.mapper.AccountMapper;
import net.lesscoding.mapper.AccountPlayerMapper;
import net.lesscoding.mapper.PlayerWeaponMapper;
import net.lesscoding.model.Player;
import net.lesscoding.model.dto.BattleDto;
import net.lesscoding.model.dto.TauntDto;
import net.lesscoding.model.vo.RedisUserCache;
import net.lesscoding.model.vo.fight.BattleProcess;
import net.lesscoding.model.vo.fight.BattleResult;
import net.lesscoding.service.BattleService;
import net.lesscoding.utils.BattleUtil;
import net.lesscoding.utils.ExpUtil;
import net.lesscoding.utils.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author eleven
 * @date 2023/9/27 12:16
 * @apiNote
 */
@Service
@Slf4j
public class BattleServiceImpl implements BattleService {

    @Resource
    private AccountPlayerMapper playerMapper;

    @Resource
    private PlayerWeaponMapper playerWeaponMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private ExpUtil expUtil;

    @Autowired
    @Qualifier("jedisRedisTemplate")
    private RedisTemplate jedisTemplate;

    @Value("${redis.userCache}")
    private String userCache;

    @Autowired
    private Gson gson;

    @Autowired
    private WebSocketUtil socketUtil;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public BattleResult doBattle(BattleDto dto) {
        List<BattleProcess> processList = new ArrayList<>();
        Player reqAttack = dto.getAttacker();
        if (reqAttack.getId() != null || StrUtil.isNotBlank(reqAttack.getMac())) {
            throw new RuntimeException("别用接口刷啊喂，如果是插件的话记得更新一下插件版本");
        }
        reqAttack.setAccountId(StpUtil.getLoginIdAsInt());
        Player attacker =  playerMapper.getPlayerBaseAttr(reqAttack);
        Player defender =  playerMapper.getPlayerBaseAttr(dto.getDefender());
        BattleUtil.doBattle(attacker, defender, processList, 1);
        processList.add(new BattleProcess(
                processList.size(),
                String.format("【战斗结果】:%s", BattleUtil.getFightResult(attacker, defender))
        ));
        expUtil.addPlayerExp(attacker, defender, processList);
        BattleResult result = new BattleResult();
        result.setSuccess(attacker.getHp() > 0);
        result.setProcessList(processList);
        result.setRivalMac(defender.getMac());
        return result;
    }

    @Override
    public String tauntRavil(TauntDto dto) throws InterruptedException {
        Map<String,String> userCacheMap = jedisTemplate.boundHashOps(userCache).entries();
        String attackerNickname = getNicknameByMac(userCacheMap, dto.getAttackMac());
        String rivalNickname = getNicknameByMac(userCacheMap, dto.getRivalMac());
        return socketUtil.tauntRival(attackerNickname, rivalNickname);
    }

    /**
     * 根据mac获取nickname
     * @param userCacheMap      缓存
     * @param mac               mac地址
     * @return String           昵称
     */
    private String getNicknameByMac(Map<String, String> userCacheMap, String mac) {
        String userCache = userCacheMap.get(mac);
        if (StrUtil.isNotBlank(userCache)) {
            RedisUserCache userVo = gson.fromJson(userCache, RedisUserCache.class);
            return userVo.getUsername();
        }
        Account account = accountMapper.selectOne(new QueryWrapper<Account>()
                .eq("mac", mac));
        return account.getNickname();
    }


}
