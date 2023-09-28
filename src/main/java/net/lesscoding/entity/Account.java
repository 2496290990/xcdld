package net.lesscoding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.lesscoding.common.BaseEntity;

/**
 * @author eleven
 * @date 2023/9/27 15:54
 * @apiNote
 */
@Data
@TableName("tb_account")
public class Account extends BaseEntity {
    private String account;

    private String mac;
}
