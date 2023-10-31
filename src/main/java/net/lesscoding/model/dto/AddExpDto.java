package net.lesscoding.model.dto;

import cn.dev33.satoken.stp.StpUtil;
import lombok.Data;

/**
 * @author eleven
 * @date 2023/10/20 9:43
 * @apiNote
 */
@Data
public class AddExpDto {
    private Integer id;

    private Integer addExp;

    private Integer updateBy;

    private Boolean isNpc;

    public AddExpDto(Integer id, Integer addExp, Boolean isNpc) {
        this.id = id;
        this.addExp = isNpc ? 0 : addExp;
        this.updateBy = StpUtil.getLoginIdAsInt();
    }
}
