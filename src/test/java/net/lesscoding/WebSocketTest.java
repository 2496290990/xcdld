package net.lesscoding;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.model.chat.ChatMsg;
import net.lesscoding.model.chat.SendDto;
import net.lesscoding.model.dto.AnnounceDto;
import net.lesscoding.utils.WebSocketUtil;
import org.assertj.core.util.Lists;
import org.checkerframework.checker.units.qual.A;
import org.java_websocket.client.WebSocketClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author eleven
 * @date 2023/11/6 14:39
 * @apiNote
 */
@SpringBootTest(classes = MainApp.class)
@RunWith(SpringRunner.class)
@RefreshScope
@Slf4j
public class WebSocketTest {

    @Autowired
    private Gson gson;

    @Autowired
    private WebSocketUtil socketUtil;
    @Test
    public void webSocket() throws Exception {
        System.out.println(socketUtil.announce(new AnnounceDto("游戏更新公告:\n\t1.测试自动给在线玩家发送公告", 0)));
    }


}
