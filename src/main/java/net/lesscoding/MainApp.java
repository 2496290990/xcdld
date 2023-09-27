package net.lesscoding;

import net.lesscoding.entity.BattleProcess;
import net.lesscoding.entity.Weapon;
import net.lesscoding.model.Player;
import net.lesscoding.utils.BattleUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author eleven
 * @date 2023年9月27日12:12:28
 */
@SpringBootApplication
@MapperScan("net.lesscoding.mapper")
public class MainApp {
    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

    @PostConstruct
    public void battleTest() {
        List<Weapon> weaponList = Collections.singletonList(new Weapon("蓝球"));
        Player attacker = new Player("IKUN", weaponList);
        Player defender = new Player("小黑子", weaponList);
        List<BattleProcess> processList = new ArrayList<>();
        BattleUtil.doBattle(attacker, defender, processList, 1);
        processList.forEach(System.out::println);
        System.out.println();
        System.out.println(attacker);
        System.out.println(defender);
    }
}
