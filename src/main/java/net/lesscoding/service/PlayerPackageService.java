package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.PlayerPackage;

import java.util.List;

/**
 * @author eleven
 * @date 2023/10/31 9:46
 * @apiNote
 */
public interface PlayerPackageService extends IService<PlayerPackage> {

    /**
     * 根据类型查看背包物品
     * @param type
     * @return
     */
    List<PlayerPackage> findByType(Integer playerId,Integer type);

    /**
     * 新增背包物品
     * @param playerPackages
     * @return
     */
    Boolean addPlayerPackage(Integer playerId,List<PlayerPackage> playerPackages);
}
