package net.lesscoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lesscoding.entity.SysDict;

import java.util.List;

/**
 * @author eleven
 * @date 2023/3/21 15:43
 * @description
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 编辑系统字典项
     * @param dict
     * @return
     */
    Integer editDict(SysDict dict);

    /**
     * 根据类型查询数据
     * @param dict  字典项
     * @return
     */
    List<SysDict> getByType(SysDict dict);
}
