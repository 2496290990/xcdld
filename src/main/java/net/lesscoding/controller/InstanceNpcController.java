package net.lesscoding.controller;

import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import net.lesscoding.model.dto.NpcFightDto;
import net.lesscoding.service.InstanceNpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eleven
 * @date 2023/10/31 9:58
 * @apiNote
 */
@RestController
@RequestMapping("/instanceNpc")
public class InstanceNpcController {
    @Autowired
    private InstanceNpcService npcService;

    @PostMapping("/challenge")
    public Result challenge(@RequestBody NpcFightDto dto) {
        return ResultFactory.success(npcService.challengeNpc(dto));
    }
}
