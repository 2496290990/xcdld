package net.lesscoding.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.lesscoding.entity.InstanceNpc;
import net.lesscoding.entity.InstanceRecord;

import java.util.List;

/**
 * @author eleven
 * @date 2023/10/31 9:37
 * @apiNote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeInstanceVo  {

    private InstanceRecord record;

    private List<InstanceNpc> npcList;
}
