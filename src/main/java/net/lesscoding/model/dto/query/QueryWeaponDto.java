package net.lesscoding.model.dto.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author eleven
 * @date 2023/11/21 15:48
 * @apiNote
 */
@Data
public class QueryWeaponDto {
    private String playerId;

    private String accountId;

    private String weaponId;

    private String weaponName;

    private Integer grade;

    private Page page;
}
