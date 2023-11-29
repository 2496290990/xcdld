package net.lesscoding.controller;

import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import net.lesscoding.model.dto.BattleDto;
import net.lesscoding.model.dto.TauntDto;
import net.lesscoding.service.BattleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 战斗接口
 *
 * @author eleven
 * @date 2023/9/27 12:15
 * @apiNote
 */
@RestController
@RequestMapping("/battle")
public class BattleController {
    @Resource
    private BattleService battleService;

    /**
     * 发起战斗
     *
     * @author 鼓励师
     * @date 2023/11/1 13:53
     */
    @PostMapping("/doBattle")
    public Result doBattle(@RequestBody BattleDto dto) {
        return ResultFactory.success(battleService.doBattle(dto));
    }

    @PostMapping("/taunt")
    public Result tauntRavil(@RequestBody TauntDto dto) throws InterruptedException {
        return ResultFactory.success(battleService.tauntRavil(dto));
    }
}
