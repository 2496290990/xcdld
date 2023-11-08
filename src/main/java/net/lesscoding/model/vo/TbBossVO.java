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
* @time 2023-11-07 16:24:15
* @Description: [BOSS表]
*/
@Data
public class TbBossVO {


	public String id;
	/**
	 * BOSS名称
	 */
	public String nickname;
	/**
	 * 参与最小等级
	 */
	public Integer joinMinLevel;
	/**
	 * 参与最大等级
	 */
	public Integer joinMaxLevel;
	/**
	 * 父类BOSS
	 */
	public Integer parentId;
	/**
	 * 父类BOSS出现概率
	 */
	public Integer probability;
	/**
	 * BOSS最小等级
	 */
	public Integer level;

}