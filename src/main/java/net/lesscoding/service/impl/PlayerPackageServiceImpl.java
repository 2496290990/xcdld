package net.lesscoding.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import net.lesscoding.entity.PlayerPackage;
import net.lesscoding.mapper.PlayerPackageMapper;
import net.lesscoding.service.PlayerPackageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author eleven
 * @date 2023/10/31 9:47
 * @apiNote
 */
@Service
public class PlayerPackageServiceImpl extends ServiceImpl<PlayerPackageMapper, PlayerPackage> implements PlayerPackageService {

    @Override
    public List<PlayerPackage> findByType(Integer type) {
        LambdaQueryWrapper<PlayerPackage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlayerPackage::getType,type);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Boolean addPlayerPackage(Integer playerId, List<PlayerPackage> playerPackages) {
        LambdaQueryWrapper<PlayerPackage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlayerPackage::getPlayerId, playerId);
        List<PlayerPackage> playerPackageList = baseMapper.selectList(queryWrapper);
        Map<Integer, List<PlayerPackage>> listMap = playerPackageList.stream().collect(Collectors.groupingBy(PlayerPackage::getObjId, Collectors.toList()));

        List<PlayerPackage> result = Lists.newArrayList();
        for (PlayerPackage playerPackage : playerPackages) {
            // todo 查询物品 并 判断物品是否可重叠
            Boolean stackable = Boolean.TRUE;
            // 可重叠
            if (stackable) {
                // 判断背包是否存在该物品
                List<PlayerPackage> packages = listMap.get(playerPackage.getObjId());
                if (CollectionUtil.isNotEmpty(packages)) {
                    PlayerPackage aPackage = packages.get(0);
                    aPackage.setNum(aPackage.getNum() + playerPackage.getNum());
                    baseMapper.updateById(aPackage);
                } else {
                    result.add(playerPackage);
                }
            } else {
                result.add(playerPackage);
            }
        }
        return this.saveOrUpdateBatch(result);
    }
}
