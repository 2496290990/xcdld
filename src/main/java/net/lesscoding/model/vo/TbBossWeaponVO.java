package net.lesscoding.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;


/**
* @author Lan
* @time 2023-11-07 14:16:20
* @Description: [boss武器表]
*/
@Data
public class TbBossWeaponVO {


	public String id;
	/**
	 * 角色id
	 */
	public Integer bossId;
	/**
	 * 武器id
	 */
	public Integer weaponId;
	/**
	 * 强化等级
	 */
	public Integer addLevel;
	/**
	 * 创建人
	 */
	public Integer createBy;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	/**
	 * 创建时间
	 */
	public LocalDateTime createTime;
	/**
	 * 更新人
	 */
	public Integer updateBy;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	/**
	 * 更新时间
	 */
	public LocalDateTime updateTime;
	/**
	 * 删除标记
	 */
	public Integer delFlag;

}