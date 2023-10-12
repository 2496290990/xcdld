package net.lesscoding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.model.Player;
import net.lesscoding.model.dto.PlayerDto;
import net.lesscoding.model.vo.PlayerVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author eleven
 * @date 2023/10/10 17:00
 * @apiNote
 */
public interface AccountPlayerMapper extends BaseMapper<AccountPlayer> {
    Player getPlayerBaseAttr(Integer accountPlayerId);

    /**
     * 查询玩家列表
     * @param page      分页参数
     * @param dto       查询参数
     * @return  List    返回数据
     */
    Page<PlayerVo> getAllPlayer(Page page, @Param("dto") PlayerDto dto);
}
