package net.lesscoding.model.dto;

import lombok.Data;
import net.lesscoding.entity.GameInstance;
import net.lesscoding.entity.InstanceNpc;

import java.util.List;

/**
 * @author eleven
 * @date 2023/11/3 15:50
 * @apiNote
 */
@Data
public class AddInstanceDto {
    private GameInstance instance;

    private List<InstanceNpc> npcList;
}
