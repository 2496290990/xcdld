package net.lesscoding.model;

import lombok.Data;
import net.lesscoding.entity.User;
import net.lesscoding.entity.Weapon;

import java.util.List;

/**
 * @author eleven
 * @date 2023/9/22 15:30
 * @apiNote
 */
@Data
public class Player {

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

    private User user;

    private List<Weapon> weaponList;

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
}
