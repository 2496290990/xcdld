package net.lesscoding.utils;

import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author eleven
 * @date 2023/10/30 14:56
 * @apiNote
 */
@Component
public class AccountUtil {

    @Value("${account.begin:10000}")
    public Integer begin;

    @Value(("${account.end:99999}"))
    public Integer end;

    /**
     * 获取一个独一无二的账号
     * @param existsAccountSet 数据库已经存在的账号
     * @return String
     */
    public String getAccountStr(Set<String> existsAccountSet) {
        List<String> initList = initAccount(begin, end, existsAccountSet);
        return RandomUtil.randomEle(initList);
    }

    public List<String> getAccountList(Set<String> existsAccountSet, Integer count) {
        List<String> initList = initAccount(begin, end, existsAccountSet);
        List<String> result = new ArrayList<>(count);
        for (Integer i = 0; i < count; i++) {
            String randomAccount = RandomUtil.randomEle(initList);
            result.add(randomAccount);
            initList.remove(randomAccount);
        }
        return result;
    }

    public List<String> initAccount(Integer begin, Integer end, Collection<String> existsAccount) {
        List<String> accoutList = new ArrayList<>();
        for (Integer account = begin; account <= end; account++) {
            accoutList.add(account.toString());
        }
        accoutList.removeAll(existsAccount);
        return accoutList;
    }
}
