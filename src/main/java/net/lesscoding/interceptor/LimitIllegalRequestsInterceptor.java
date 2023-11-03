package net.lesscoding.interceptor;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * @author eleven
 * @date 2023/11/3 15:06
 * @apiNote 限制非法请求
 */
@Configuration
public class LimitIllegalRequestsInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ArrayList<String> ignoreRequest = Lists.newArrayList("apipost", "postman", "apifox");
        String userAgent = request.getHeader("User-agent").toLowerCase();
        if (ignoreRequest.stream().anyMatch(userAgent::contains)) {
            throw new RuntimeException("请勿使用Postman，ApiPost，ApiFox等工具调用接口");
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
