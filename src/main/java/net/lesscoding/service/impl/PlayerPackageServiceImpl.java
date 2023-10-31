package net.lesscoding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.PlayerPackage;
import net.lesscoding.mapper.PlayerPackageMapper;
import net.lesscoding.service.PlayerPackageService;
import org.springframework.stereotype.Service;

/**
 * @author eleven
 * @date 2023/10/31 9:47
 * @apiNote
 */
@Service
public class PlayerPackageServiceImpl extends ServiceImpl<PlayerPackageMapper, PlayerPackage> implements PlayerPackageService {
}
