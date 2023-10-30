package net.lesscoding.utils;

import cn.hutool.core.util.RandomUtil;

import java.util.Set;

/**
 * @author eleven
 * @date 2023/10/30 14:56
 * @apiNote
 */
public class AccountUtil {

    /**
     * 获取一个独一无二的账号
     * @param existsAccountSet 数据库已经存在的账号
     * @return String
     */
    public static String getAccountStr(Set<String> existsAccountSet) {
        String account = "";
        do {
            account = String.valueOf(RandomUtil.randomInt(9_999, 100_000));
        } while (!existsAccountSet.contains(account));
        existsAccountSet.add(account);
        return account;
    }
}
