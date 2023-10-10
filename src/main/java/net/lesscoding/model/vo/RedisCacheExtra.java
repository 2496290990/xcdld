package net.lesscoding.model.vo;

import lombok.Data;

/**
 * @author eleven
 * @date 2023/10/10 16:11
 * @apiNote
 */
@Data
public class RedisCacheExtra {
    /**
     * uuid : 66-B2-FC-90-64-04
     * id : 0242acfffe110004-00000006-00000723-53e1f2ef99cc4749-110f8f88
     * username : {demo}
     * status : FISHING
     * shortRegion : 京
     * role : USER
     * permit : 3
     * platform : IDEA
     * clientVersion : 1.6.7-beta0
     */

    private String uuid;
    private String id;
    private String username;
    private String status;
    private String shortRegion;
    private String role;
    private int permit;
    private String platform;
    private String clientVersion;

}
