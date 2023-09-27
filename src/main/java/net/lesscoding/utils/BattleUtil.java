package net.lesscoding.utils;

import cn.hutool.core.lang.WeightRandom;
import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import net.lesscoding.entity.BattleProcess;
import net.lesscoding.entity.Weapon;
import net.lesscoding.model.BattleResult;
import net.lesscoding.model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author eleven
 * @date 2023/9/22 15:58
 * @apiNote 战斗工具类
 */
public class BattleUtil {
    /**
     * 传入两个对战者的mac 从数据查询相应的玩家信息转换成Player对象
     * PVE npc不增加经验
     * @param attackerMac       攻击者Mac
     * @param defenderMac       被攻击者Mac
     * @param isPvp             是否PVP
     * @return BattleResult     对战结果
     */
    public static BattleResult doBattle(String attackerMac, String defenderMac, boolean isPvp) {
        BattleResult result = new BattleResult();

        return result;
    }

    /**
     * 战斗工具类
     * @param attacker      攻击者
     * @param defender      被攻击者
     * @param processList   进程集合
     * @param round         回合数
     */
    public static void doBattle(Player attacker, Player defender, List<BattleProcess> processList, int round) {
        while(true) {
            if(attacker.getHp() <= 0 || defender.getHp() <= 0) {
                break;
            }
            StringBuffer sb = new StringBuffer();
            sb.append(String.format("【第%d回合】", round));
            int attack = attacker.getAttack();
            // 是否使用武器
            Boolean useWeaponFlag = getWeightResult(0.5);
            if (useWeaponFlag) {
                Weapon weapon = RandomUtil.randomEle(attacker.getWeaponList());
                sb.append(String.format("玩家[%s]使用了武器[%s],", attacker.getNickname(), weapon.getName()));
                Integer rangeAttack = getRangeAttack(weapon.getMinDamage(), weapon.getMaxDamage());
                attack += rangeAttack;
                sb.append(String.format("攻击力提升了%d,当前%d,", rangeAttack, attack));
            }
            Boolean fleeFlag = getWeightResult(defender.getFlee());
            if (fleeFlag) {
                sb.append(String.format("%s侧身一躲，躲过了%s的致命一击", defender.getNickname(), attacker.getNickname()));
            } else {
                attack -= defender.getDefence();
                defender.setHp(Math.max(0, defender.getHp() - attack));
                sb.append(String.format("%s被击中, HP减少 %d, 当前 %d", defender.getNickname(), attack, defender.getHp()));
            }
            round++;
            processList.add(new BattleProcess(round, sb.toString()));
            Boolean comboFlag = getWeightResult(attacker.getComboRate());
            if (comboFlag) {
                doBattle(attacker, defender, processList, round);
            } else {
                doBattle(defender, attacker, processList, round);
            }
        }
    }

    /**
     * 获取区间数值 [min,max] 区间的数值
     * @param min   最小值(包含)
     * @param max   最大值(不包含)
     * @return Integer 最终数据
     */
    private static Integer getRangeAttack(Integer min, Integer max) {
        return RandomUtil.randomInt(min - 1, max + 1);
    }

    /**
     * 权重随机数
     * @param weight        权重值
     * @return Boolean
     */
    private static Boolean getWeightResult(double weight) {
        if(weight < 0) {
            weight = 0;
        }
        if(weight > 1) {
            weight = 1;
        }
        ArrayList<WeightRandom.WeightObj<Boolean>> list = Lists.newArrayList(
                new WeightRandom.WeightObj<>(true, weight),
                new WeightRandom.WeightObj<>(false, 1 - weight)
        );
        return RandomUtil.weightRandom(list).next();
    }

    public static void main(String[] args) {
        List<Weapon> weaponList = Collections.singletonList(new Weapon("蓝球"));
        Player attacker = new Player("IKUN", weaponList);
        Player defender = new Player("小黑子", weaponList);
        List<BattleProcess> processList = new ArrayList<>();
        doBattle(attacker, defender, processList, 1);
        processList.forEach(System.out::println);
        System.out.println();
        System.out.println(attacker);
        System.out.println(defender);
    }
}
