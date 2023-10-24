package net.lesscoding.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.cache.Cache;
import cn.hutool.cache.impl.TimedCache;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.exception.LimiterException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author eleven
 * @date 2023/2/22 15:29
 * @description
 */
@Slf4j
public class RateLimiterInterceptor implements HandlerInterceptor {

    // 缓存 超时未使用会被移除
    private final Cache<String, RateLimiter> rateLimiterCache = new TimedCache<>(20 * 60 * 1000);

    public RateLimiterInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String id = StpUtil.getLoginIdAsString();
        RateLimiter rateLimiter = rateLimiterCache.get(id);
        if (rateLimiter == null) {
            rateLimiter = RateLimiter.create(1, 10, TimeUnit.SECONDS);
            rateLimiterCache.put(id, rateLimiter);
        }

        if (rateLimiter.tryAcquire()) {
            // 成功获取到令牌
            return true;
        }
        throw new LimiterException("调用太频繁，请稍后再试！");
    }
}
