package net.lesscoding;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.model.dto.AnnounceDto;
import net.lesscoding.utils.WebSocketUtil;
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
        socketUtil.announce(
                new AnnounceDto(
                        "游戏更新公告:\n" +
                                "1.战斗结果返回变更\n" +
                                "2.新增战胜对手发送鱼塘嘲讽功能\n" +
                                "3.增加了游戏背包查看功能",
                        0));
    }


}
