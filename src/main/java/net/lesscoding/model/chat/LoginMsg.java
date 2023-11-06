package net.lesscoding.model.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author eleven
 * @date 2023/11/6 16:17
 * @apiNote
 */
@Data
@AllArgsConstructor
public class LoginMsg {

    private String username;
    private String status;
    private Boolean reconnected;
    private String pluginVersion;
    private String token;
    private String uuid;
    private String platform;

    public LoginMsg() {
        this.username = "爱坤公告发布";
        this.platform = "IDEA";
        this.reconnected = false;
        this.uuid = "FF-FF-FF-FF-FF";
        pluginVersion = "1.6.7-beta";
    }
}
