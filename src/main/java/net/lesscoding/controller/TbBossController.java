package net.lesscoding.controller;

import javax.annotation.Resource;

import net.lesscoding.model.dto.BossDto;
import net.lesscoding.model.vo.TbBossVO;
import net.lesscoding.service.impl.TbBossServiceImpl;
import org.springframework.web.bind.annotation.*;
import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;

/**
* boss表模块
* @author Lan
* @time 2023-11-07 14:15:28
*/
@RestController
@RequestMapping("/tbBoss")
public class TbBossController{

	@Resource
	private TbBossServiceImpl tbBossServiceImpl;

	/**
	* boss表列表
	* @param tbBossVO
	* @return
	*/
	@PostMapping("/list")
	public Result list(@RequestBody TbBossVO tbBossVO){
		return ResultFactory.success(tbBossServiceImpl.list());
	}

	/**
	* 挑战boss
	* @return
	*/
	@GetMapping("/challenge")
	public Result challenge(BossDto bossDto) {
		return ResultFactory.success("");
	}


}
