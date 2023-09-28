package net.lesscoding.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author eleven
 * @date 2023/9/27 15:54
 * @apiNote
 */
@Data
public class BaseEntity<T> {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/ShangHai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:dd")
    private LocalDateTime createTime;

    private Integer updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/ShangHai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:dd")
    private LocalDateTime updateTime;

    private Boolean delFlag;
}
