package net.lesscoding.controller;

import net.lesscoding.aspect.Log;
import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import net.lesscoding.entity.SysDict;
import net.lesscoding.enums.BusinessType;
import net.lesscoding.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author eleven
 * @date 2023/3/21 15:44
 * @description
 */

@RestController
@RequestMapping("/dict")

public class SysDictController {

    @Autowired
    private SysDictService dictService;

    @PostMapping("/edit")
    @Log(title = "编辑系统字典项", businessType = BusinessType.UPDATE)
    public Result editDict(@RequestBody SysDict dict){
        return ResultFactory.success(dictService.editDict(dict));
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") Integer id){
        return ResultFactory.success(dictService.getById(id));
    }

    @DeleteMapping("/{id}")
    @Log(title = "根据id删除字典项", businessType = BusinessType.DELETE)
    public Result delById(@PathVariable("id") Integer id){
        return ResultFactory.success(dictService.removeById(id));
    }

    @GetMapping("/getByType")
    public Result getByType(SysDict dict){
        return ResultFactory.success(dictService.getByType(dict));
    }


}
