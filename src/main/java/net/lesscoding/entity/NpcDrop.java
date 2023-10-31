package net.lesscoding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/10/31 9:07
 * @apiNote
 */
@Data
@TableName("tb_npc_drop")
public class NpcDrop extends BaseEntity<NpcDrop> {

    /**
     * 名称
     */
    private String name;

    /**
     * 掉落概率
     */
    private Double dropProbability;

    /**
     * 等级 0 白 1蓝 2紫 3粉 4传说 5史诗 6神话 7贴膜
     */
    private Integer grade;
}
