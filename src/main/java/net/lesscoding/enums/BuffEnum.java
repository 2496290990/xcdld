package net.lesscoding.enums;

/**
 * @author eleven
 * @date 2023/10/17 17:00
 * @apiNote
 */
public enum BuffEnum {
    /**
     * 攻击力BUFF
     */
    ATTACK("攻击", "attack"),

    DEFENDER("防御", "defender"),
    ;
    /**
     * Buff名称
     */
    private String buffName;

    /**
     * 影响属性
     */
    private String effectAttr;


    private BuffEnum(String buffName, String effectAttr) {
        this.buffName = buffName;
        this.effectAttr = effectAttr;
    }
}
