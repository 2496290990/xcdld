package net.lesscoding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.PlayerPackage;
import net.lesscoding.mapper.PlayerPackageMapper;
import net.lesscoding.service.PlayerPackageService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author eleven
 * @date 2023/10/31 9:47
 * @apiNote
 */
@Service
public class PlayerPackageServiceImpl extends ServiceImpl<PlayerPackageMapper, PlayerPackage> implements PlayerPackageService {


    @Override
    public List<PlayerPackage> findByType(Integer playerId,Integer type) {
        LambdaQueryWrapper<PlayerPackage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlayerPackage::getPlayerId,playerId);
        queryWrapper.eq(PlayerPackage::getType,type);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Boolean addPlayerPackage(Integer playerId, List<PlayerPackage> playerPackages) {
        return false;
    }
}
