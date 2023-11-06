package net.lesscoding.model.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author eleven
 * @date 2023/11/6 15:13
 * @apiNote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMsg {

    private String content;

    private String msgType = "TEXT";

    private List<String> toUsers;
}
