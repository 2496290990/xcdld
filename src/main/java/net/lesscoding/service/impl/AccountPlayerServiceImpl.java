package net.lesscoding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.mapper.AccountPlayerMapper;
import net.lesscoding.service.AccountPlayerService;
import org.springframework.stereotype.Service;

/**
 * @author eleven
 * @date 2023/10/10 16:59
 * @apiNote
 */
@Service
public class AccountPlayerServiceImpl extends ServiceImpl<AccountPlayerMapper, AccountPlayer> implements AccountPlayerService {
}
