package net.lesscoding.controller;

import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import net.lesscoding.model.dto.PlayerDto;
import net.lesscoding.service.AccountPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
