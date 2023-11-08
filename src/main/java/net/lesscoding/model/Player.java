package net.lesscoding.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.lesscoding.entity.InstanceNpc;
import net.lesscoding.entity.Weapon;
import net.lesscoding.model.vo.SkillVo;

import java.util.List;

/**
 * @author eleven
 * @date 2023/9/22 15:30
 * @apiNote
 */
@Data
@NoArgsConstructor
public class Player {
    private Integer id;

    private Integer accountId;

    private Integer level;

    private String mac;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 血量
     */
    private Integer hp;

    /**
     * 攻击力
     */
    private Integer attack;

    /**
     * 防御力
     */
    private Integer defence;

    /**
     * 命中率
     */
    private Double hitRate;

    /**
     * 闪避率
     */
    private Double flee;

    /**
     * 连击率
     */
    private Double comboRate;
    /**
     * 暴击率
     */
    private Double criticalChance;

    private List<Weapon> weaponList;

    private List<SkillVo> skillList;

    private Boolean isNpc = false;


    public Player(String nickname, List<Weapon> weaponList) {
        this.nickname = nickname;
        this.hp = 100;
        this.attack = 5;
        this.defence = 3;
        this.hitRate = 0.5;
        this.flee = 0.4;
        this.comboRate = 0.2;
        this.weaponList = weaponList;
    }

    /**
     * 根据NPC属性增幅对应的玩家模板
     * @param npc
     */
    public void increaseNpcAttr(InstanceNpc npc) {
        Double increaseRatio = npc.getIncreaseRatio();
        setNickname(npc.getNpcName());
        setIsNpc(true);
        setHp((int)(getHp() * increaseRatio));
        setAttack((int)(getAttack() * increaseRatio));
        setDefence((int)(getDefence() * increaseRatio));
        setHitRate(getHitRate() * increaseRatio);
        setFlee(getFlee() * increaseRatio);
        setComboRate(getComboRate() * increaseRatio);
        setCriticalChance(getCriticalChance() * increaseRatio);
    }
}
