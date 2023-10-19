package net.lesscoding.model.vo;

import lombok.Data;
import net.lesscoding.enums.BuffEnum;


/**
 * @author eleven
 * @date 2023/10/17 17:00
 * @apiNote
 */
@Data
public class BuffVo {
    private Boolean buff;

    private Boolean fixed;

    private Double addNum;

    /**
     *
     */
    private BuffEnum buffEnum;

    /**
     * 影响回合数
     */
    private Integer effectRound;
}
