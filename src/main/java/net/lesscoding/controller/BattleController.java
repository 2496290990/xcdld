package net.lesscoding.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import net.lesscoding.model.dto.BattleDto;
import net.lesscoding.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eleven
 * @date 2023/9/27 12:15
 * @apiNote
 */
@RestController
@RequestMapping("/battle")
public class BattleController {
    @Autowired
    private BattleService battleService;

    @PostMapping("/doBattle")
    private Result doBattle(@RequestBody BattleDto dto) {
        return ResultFactory.success(battleService.doBattle(dto));
    }
}
