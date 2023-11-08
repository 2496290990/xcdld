package net.lesscoding.model.vo;

import lombok.Data;

/**
 * @author eleven
 * @date 2023/11/8 21:02
 * @apiNote
 */
@Data
public class SkillVo {
    private Integer id;

    private String name;

    private String effectJson;

    private Integer minDamage;

    private Integer maxDamage;
}
