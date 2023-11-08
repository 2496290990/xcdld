package net.lesscoding.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import net.lesscoding.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lesscoding.common.Consts;

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
	/**
	 * 概率
	 */
	@TableField("probability")
	private Integer probability;

	public TbBossReward() {
	}

	public TbBossReward(Integer type, Integer number) {
		this.type = type;
		this.number = number;
	}

	public String getRewardName() {
		if (Consts.INT_STATE_1.equals(type)){
			return "金币";
		}
		return "";
	}
}