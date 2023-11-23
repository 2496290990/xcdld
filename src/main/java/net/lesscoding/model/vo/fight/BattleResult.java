package net.lesscoding.model.vo.fight;

import lombok.Data;

import java.util.List;

/**
 * @author eleven
 * @date 2023/9/22 15:52
 * @apiNote 战斗结果
 */
@Data
public class BattleResult {
    /**
     * 是否成功
     */
    private Boolean success;

    private List<BattleProcess> processList;
}
