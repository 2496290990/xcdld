package net.lesscoding.controller;

import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import net.lesscoding.model.dto.JoinInstanceDto;
import net.lesscoding.service.GameInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/join")
    public Result joinInstance(@RequestBody JoinInstanceDto dto) {
        return ResultFactory.success(instanceService.joinInstance(dto));
    }
}
