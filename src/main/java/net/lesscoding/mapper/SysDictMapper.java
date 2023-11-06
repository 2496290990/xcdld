package net.lesscoding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lesscoding.entity.SysDict;

/**
 * @author eleven
 * @date 2023/3/21 15:42
 * @description
 */
public interface SysDictMapper extends BaseMapper<SysDict> {
    /**
     * 根据编码查询最大的排序
     *
     * @param dict 字典项
     * @return
     */
    Integer selectMaxSort(SysDict dict);

}