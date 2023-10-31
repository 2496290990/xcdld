package net.lesscoding.model.vo;

import lombok.Data;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.entity.InstanceNpc;

/**
 * @author eleven
 * @date 2023/10/31 10:12
 * @apiNote
 */
@Data
public class NpcPlayerVo {
    private InstanceNpc npc;

    private AccountPlayer player;
}
