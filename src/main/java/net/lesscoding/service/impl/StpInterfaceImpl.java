package net.lesscoding.service.impl;


import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author eleven
 * @date 2022/11/16 18:52
 * @description 自定义权限验证接口扩展
 * 保证此类被SpringBoot扫描，完成Sa-Token的自定义权限验证扩展
 */
@Component
public class StpInterfaceImpl implements StpInterface {



    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return Collections.singletonList("USER:*");
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return Collections.singletonList("USER");
    }


}

