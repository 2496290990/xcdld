package net.lesscoding.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author eleven
 * @date 2023/9/22 15:57
 * @apiNote 战斗过程
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BattleProcess {
    /**
     * 序号
     */
    private Integer index;

    /**
     * 过程
     */
    private String process;
}
