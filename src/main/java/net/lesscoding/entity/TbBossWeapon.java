package net.lesscoding.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import net.lesscoding.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;


/**
 * @author Lan
 * @time 2023-11-07 14:16:20
 * @Description: [boss武器表： tb_boss_weapon]
 */
@Data
public class TbBossWeapon extends BaseEntity<TbBossWeapon> {

	/**
	 * 角色id
	 */
	@TableField("boss_id")
	private Integer bossId;
	/**
	 * 武器id
	 */
	@TableField("weapon_id")
	private Integer weaponId;
	/**
	 * 强化等级
	 */
	@TableField("add_level")
	private Integer addLevel;

	
}