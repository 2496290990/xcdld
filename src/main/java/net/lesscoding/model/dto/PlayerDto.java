package net.lesscoding.model.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author eleven
 * @date 2023/10/12 21:56
 * @apiNote
 */
@Data
public class PlayerDto {

    private String nickname;

    private Page page;

}
