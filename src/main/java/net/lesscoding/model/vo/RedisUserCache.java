package net.lesscoding.model.vo;

import lombok.Data;

/**
 * @author eleven
 * @date 2023/10/9 20:16
 * @apiNote
 */
@Data
public class RedisUserCache {
    /**
     * 昵称
     */
    private String username;
    /**
     * 状态
     */
    private String state;
    /**
     * ip地址
     */
    private String ip;
    /**
     * 区域
     */
    private String region;
    /**
     *角色
     */
    private String role;
    /**
     * mac地址
     */
    private String uuid;
    /**
     *平台
     */
    private String platform;
    /**
     * 客户端版本
     */
    private String clientVersion;
}
