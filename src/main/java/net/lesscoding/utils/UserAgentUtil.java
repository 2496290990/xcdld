package net.lesscoding.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @author eleven
 * @date 2022/12/30 18:37
 * @apiNote $
 */
@Slf4j
public class UserAgentUtil {
    private static HttpServletRequest getHttpRequest(){
        return ServletUtils.getRequest();
    }

    public static String userAgent(HttpServletRequest request){
        if (null == request){
            request = getHttpRequest();
        }
        String userAgent = request.getHeader("user-agent");
        log.info("当前请求标识-{}",userAgent);
        return userAgent;
    }

    public static String  userAgent(){
        return userAgent(null);
    }
}
