package net.lesscoding.utils;

import com.google.gson.Gson;
import net.lesscoding.enums.Action;
import net.lesscoding.mapper.SysDictMapper;
import net.lesscoding.model.chat.LoginMsg;
import net.lesscoding.model.chat.SendDto;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author eleven
 * @date 2023/11/6 16:07
 * @apiNote
 */
@Configuration
public class MyWebSocketUtil {
    @Autowired
    private SysDictMapper sysDictMapper;

    @Bean
    public WebSocketClient webSocketClient() {
        WebSocketClient client = null;
        try {
            // 替换为你的WebSocket服务器地址和端口
            URI serverUri = new URI("ws://103.153.101.174:33859/xechat");
            client = new WebSocketClient(serverUri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("连接已建立");
                    send(new Gson().toJson(new SendDto<>("LOGIN", new LoginMsg())));
                }

                @Override
                public void onMessage(String message) {
                    System.out.println("收到消息： " + message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("连接已关闭");
                }

                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                }
            };
            client.connect();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return client;
    }
}
