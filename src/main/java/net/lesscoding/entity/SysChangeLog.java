package net.lesscoding.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import net.lesscoding.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


/**
 * @author Lan
 * @time 2023-11-07 17:30:49
 * @Description: [公告表： sys_change_log]
 */
@Data
public class SysChangeLog extends BaseEntity<SysChangeLog> {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 公告内容
	 */
	@TableField("content")
	private String content;
	/**
	 * 创建人
	 */
	@TableField("create_by")
	private Integer createBy;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("create_time")
	private LocalDateTime createTime;
	/**
	 * 更新人
	 */
	@TableField("update_by")
	private Integer updateBy;
	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("update_time")
	private LocalDateTime updateTime;
	/**
	 * 删除标记
	 */
	@TableField("del_flag")
	private Integer delFlag;
	
}