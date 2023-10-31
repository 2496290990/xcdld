package net.lesscoding.model.dto;

import lombok.Data;
import net.lesscoding.entity.InstanceNpc;

/**
 * @author eleven
 * @date 2023/10/31 10:00
 * @apiNote
 */
@Data
public class NpcFightDto {

    private String playerMac;

    private InstanceNpc npc;
}
