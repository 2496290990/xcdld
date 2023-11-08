package net.lesscoding.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import net.lesscoding.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;


/**
 * @author Lan
 * @time 2023-11-07 14:15:52
 * @Description: [boss奖励表： tb_boss_reward]
 */
@Data
public class TbBossReward extends BaseEntity<TbBossReward> {

	/**
	 * BOSS_Id
	 */
	@TableField("boss_id")
	private Integer bossId;
	/**
	 * 奖励类型
	 */
	@TableField("type")
	private Integer type;
	/**
	 * 数量
	 */
	@TableField("number")
	private Integer number;

	
}