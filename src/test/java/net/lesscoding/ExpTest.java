package net.lesscoding;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.SameLen;

/**
 * @author eleven
 * @date 2023/10/20 8:39
 * @apiNote
 */
@Slf4j
public class ExpTest {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            log.info("======================");
            log.info("当前生成第{}次", i);
            Player player = new Player();
            Player player2 = new Player();
            log.info("玩家一 等级{} 血量{}", player.getLv(), player.getHp());
            log.info("玩家二 等级{} 血量{}", player2.getLv(), player2.getHp());
            int exp = 1;
            int levelDiff = Math.abs(player2.getLv() - player.getLv());
            int hpExp = Math.abs(player2.getHp() - player.getHp()) / (levelDiff + 1);
            exp = levelDiff +  2 * hpExp / (levelDiff + 1 )  + 1;
            log.info("当前exp {} ", exp);
        }
    }
}
