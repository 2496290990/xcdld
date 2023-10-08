package net.lesscoding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/10/8 12:50
 * @apiNote
 */
@Data
@TableName("tb_skill")
public class Skill extends BaseEntity<Skill> {
    /**
     * 技能类型 true 主动技能 false 被动技能
     */
    private Boolean activeSkill;

    /**
     * 技能名称
     */
    private String name;

    /**
     * 技能描述
     */
    private String intro;

    /**
     * 技能品级
     */
    private Integer grade;

    /**
     * 是否固定伤害 true 固定伤害 false 百分比提升
     */
    private Boolean fixed;
}
