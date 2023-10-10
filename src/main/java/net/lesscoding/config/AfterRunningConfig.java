package net.lesscoding.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author eleven
 * @date 2023/10/10 15:37
 * @apiNote
 */
@Slf4j
//@Configuration
public class AfterRunningConfig implements ApplicationRunner {

    @Autowired
    private SystemService systemService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("ups-pool-%d").build();
        ExecutorService poolExecutor = new ThreadPoolExecutor(4, 8,
                30L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(4), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        TimeInterval timer = DateUtil.timer();
        poolExecutor.submit(() -> {
            log.info("根据mac自动注册逻辑开始");
            Integer regCount = systemService.autoRegisterByRedisMac();
            log.info("根据mac自动注册逻辑结束，自动注册账号数量 -{},用时-{}ms", regCount, timer.interval());
        });

    }
}
