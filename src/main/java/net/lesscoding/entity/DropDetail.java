package net.lesscoding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/10/31 9:04
 * @apiNote
 */
@Data
@TableName("tb_drop_detail")
public class DropDetail extends BaseEntity<DropDetail> {

    /**
     * npc掉落id
     */
    private Integer npcDropId;

    /**
     * 物品id
     */
    private Integer objId;

    /**
     * 类型 0消耗品 1技能 2武器
     */
    private Integer type;

    /**
     * 掉落概率
     */
    private Double dropProbability;
}
