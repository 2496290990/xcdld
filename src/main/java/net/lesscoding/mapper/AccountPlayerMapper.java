package net.lesscoding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.model.Player;
import net.lesscoding.model.dto.AddExpDto;
import net.lesscoding.model.dto.PlayerDto;
import net.lesscoding.model.vo.AfterPlayerVo;
import net.lesscoding.model.vo.PlayerVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author eleven
 * @date 2023/10/10 17:00
 * @apiNote
 */
public interface AccountPlayerMapper extends BaseMapper<AccountPlayer> {
    Player getPlayerBaseAttr(Player player);

    /**
     * 查询玩家列表
     * @param page      分页参数
     * @param dto       查询参数
     * @return  List    返回数据
     */
    Page<PlayerVo> getAllPlayer(Page page, @Param("dto") PlayerDto dto);

    /**
     * 给玩家添加经验
     * @param addExpList
     */
    void addPlayerExpBatch(@Param("list") List<AddExpDto> addExpList);

    void addPlayerExp(AddExpDto dto);

    /**
     * 获取获得经验经验之后的数据
     * @param asList 要查询的玩家集合
     * @return
     */
    List<AfterPlayerVo> selectAfterPlayer(List<Integer> asList);
}
