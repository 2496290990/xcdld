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

	
}