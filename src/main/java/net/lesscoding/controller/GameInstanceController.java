package net.lesscoding.controller;

import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import net.lesscoding.entity.GameInstance;
import net.lesscoding.service.GameInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author eleven
 * @date 2023/10/24 8:37
 * @apiNote 副本控制器
 */
@RestController
@RequestMapping("/instance")
public class GameInstanceController {
    @Autowired
    private GameInstanceService instanceService;

    @PostMapping("/list")
    public Result getInstanceList() {
        return ResultFactory.success(instanceService.getInstanceList());
    }

    @PostMapping("/npcList")
    public Result instanceNpcList(@RequestBody GameInstance instance) {
        return ResultFactory.success(instanceService.instanceNpcList(instance));
    }
}
