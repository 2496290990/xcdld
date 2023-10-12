package net.lesscoding.model.vo;

import lombok.Data;

/**
 * @author eleven
 * @date 2023/10/12 22:05
 * @apiNote
 */
@Data
public class PlayerVo{
    private Integer id;

    private Integer accountId;

    private String nickname;

    private Integer level;

    private String region;

}
