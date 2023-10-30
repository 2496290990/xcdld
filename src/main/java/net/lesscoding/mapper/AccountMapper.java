package net.lesscoding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lesscoding.entity.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author eleven
 * @date 2023/9/27 15:58
 * @apiNote
 */
public interface AccountMapper extends BaseMapper<Account> {
    /**
     * 获取所有的账号
     * @param o
     * @return
     */
    Set<String> selectAccountSet(Object o);

    /**
     * 批量变更账号密码
     * @param accounts
     */
    void updateAccountAndPwd(@Param("list") List<Account> accounts);
}
