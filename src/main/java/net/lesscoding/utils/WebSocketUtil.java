package net.lesscoding.utils;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.entity.Account;
import net.lesscoding.entity.SysDict;
import net.lesscoding.mapper.AccountMapper;
import net.lesscoding.mapper.SysDictMapper;
import net.lesscoding.model.chat.ChatMsg;
import net.lesscoding.model.chat.LoginMsg;
import net.lesscoding.model.chat.SendDto;
import net.lesscoding.model.dto.AnnounceDto;
import net.lesscoding.model.dto.LoginDto;
import net.lesscoding.model.vo.RedisUserCache;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author eleven
 * @date 2023/11/6 16:07
 * @apiNote
 */
@Configuration
@Slf4j
@Lazy
public class WebSocketUtil {
    @Autowired

    private SysDictMapper sysDictMapper;

    @Autowired
    @Qualifier("jedisRedisTemplate")
    private RedisTemplate jedisTemplate;

    @Autowired
    private AccountMapper accountMapper;

    @Value("${redis.userCache}")
    private String userCache;

    @Autowired
    private Gson gson;

    private WebSocketClient webSocketClient() {
        WebSocketClient client = null;
        try {
            SysDict sysDict = sysDictMapper.selectByTypeAndCode("service", "prodWebSocket");
            // 替换为你的WebSocket服务器地址和端口
            URI serverUri = new URI(sysDict.getDictValue());
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

    public Object announce(AnnounceDto dto) throws InterruptedException {
        WebSocketClient client = webSocketClient();
        // 休眠三秒等待ws连接
        Thread.sleep(3000);
        List<String> loginIds = StpUtil.searchSessionId("", 0, -1, false);
        if (CollUtil.isNotEmpty(loginIds)) {
            loginIds = loginIds.stream()
                    .map(item -> item.replace("satoken:login:session:", ""))
                    .collect(Collectors.toList());
            log.info("当前正在登录的id有 {}", loginIds);
            List<Account> loginAccountList = accountMapper.selectList(new QueryWrapper<Account>()
                    .in("id", loginIds));
            log.info("当前正在登录的账号有 {}", loginAccountList);
            Set<String> loginMacSet = loginAccountList.stream()
                    .map(Account::getMac)
                    .collect(Collectors.toSet());
            Map<String, String> userCacheMap = jedisTemplate.boundHashOps(userCache).entries();
            List<String> toUsers = new ArrayList<>(loginMacSet.size());
            for (String key : userCacheMap.keySet()) {
                if (loginMacSet.contains(key)) {
                    RedisUserCache userVo = gson.fromJson(userCacheMap.get(key), RedisUserCache.class);
                    String username = userVo.getUsername();
                    toUsers.add(username);
                }
            }
            ChatMsg chatMsg = new ChatMsg();
            chatMsg.setContent(dto.getContent());
            chatMsg.setToUsers(toUsers);
            SendDto sendDto = new SendDto<>("CHAT", chatMsg);
            String text = gson.toJson(sendDto);
            log.info("当前发送的消息{}", text);
            client.getConnection().send(text);
            Thread.sleep(dto.getDelayMinutes());
        }
        // 最后退出关闭连接
        client.close();
        return "success";
    }
}
