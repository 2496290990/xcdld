package net.lesscoding;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.enums.Action;
import net.lesscoding.model.chat.ChatMsg;
import net.lesscoding.model.chat.LoginMsg;
import net.lesscoding.model.chat.SendDto;
import net.lesscoding.utils.MyWebSocketUtil;
import org.assertj.core.util.Lists;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;


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
    private WebSocketClient socketClient;
    @Test
    public void webSocket() throws Exception {
        if (socketClient.isClosed()) {
            socketClient.connect();
        }
        Thread.sleep(3000);
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setContent("游戏更新公告:请尽快退出大乐斗，以防插件卡死\n\t1.修复了副本列表BUG\n\t2.测试公告");
        chatMsg.setToUsers(Lists.newArrayList("momoyu", "短途游", "monopoly"));
        SendDto sendDto = new SendDto<>("CHAT", chatMsg);
        String text = gson.toJson(sendDto);
        log.info("当前发送的消息{}", text);
        socketClient.getConnection().send(text);
    }


}
