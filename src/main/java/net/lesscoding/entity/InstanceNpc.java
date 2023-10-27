package net.lesscoding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/10/19 16:06
 * @apiNote
 */
@Data
@TableName("tb_instance_npc")
public class InstanceNpc extends BaseEntity<InstanceNpc> {
    private Integer instanceId;

    private String npcName;

    private Integer playerId;

    private Integer npcLevel;

    private Boolean bossFlag;

    private Double increaseRatio;

    private Integer floor;

}
