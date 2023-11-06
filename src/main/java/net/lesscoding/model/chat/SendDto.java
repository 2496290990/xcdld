package net.lesscoding.model.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author eleven
 * @date 2023/11/6 15:11
 * @apiNote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendDto<T> {


    private String action;
    private T body;

}
