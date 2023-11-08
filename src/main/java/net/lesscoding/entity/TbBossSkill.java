package net.lesscoding.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import net.lesscoding.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;


/**
 * @author Lan
 * @time 2023-11-07 14:16:07
 * @Description: [boss技能表： tb_boss_skill]
 */
@Data
public class TbBossSkill extends BaseEntity<TbBossSkill> {

	/**
	 * 角色id
	 */
	@TableField("boss_id")
	private Integer bossId;
	/**
	 * 技能id
	 */
	@TableField("skill_id")
	private Integer skillId;
	/**
	 * 技能等级
	 */
	@TableField("skill_level")
	private Integer skillLevel;

	
}