package net.lesscoding.entity;

import lombok.Data;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/10/19 16:03
 * @apiNote
 */
@Data
public class GameInstance extends BaseEntity<GameInstance> {
    /**
     * 副本名称
     */
    private String instanceName;

    /**
     * 副本描述
     */
    private String instanceInfo;

    /**
     * 层数
     */
    private Integer floorNum;

    /**
     * 准入等级
     */
    private Integer accessLevel;
}
