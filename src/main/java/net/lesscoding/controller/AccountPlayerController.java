package net.lesscoding.controller;

import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import net.lesscoding.entity.AccountPlayer;
import net.lesscoding.model.dto.PlayerDto;
import net.lesscoding.service.AccountPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author eleven
 * @date 2023/10/10 16:59
 * @apiNote
 */
@RestController
@RequestMapping("/player")
public class AccountPlayerController {

    @Autowired
    private AccountPlayerService playerService;

    @PostMapping("/getAll")
    public Result getAllPlayer(@RequestBody PlayerDto dto) {
        return ResultFactory.success(playerService.getAllPlayer(dto));
    }

    @PostMapping("/detail")
    public Result getPlayerDetail(@RequestBody AccountPlayer dto) {
        return ResultFactory.success(playerService.getPlayerDetail(dto));
    }

    @PutMapping("/recovery")
    public Result recoveryEnergy() throws IOException {
        return ResultFactory.success(playerService.selfRecovery());
    }
}
