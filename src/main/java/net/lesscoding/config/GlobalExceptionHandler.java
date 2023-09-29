package net.lesscoding.config;

//import cn.dev33.satoken.exception.NotLoginException;
//import cn.dev33.satoken.exception.NotRoleException;
//import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import net.lesscoding.common.Result;
import net.lesscoding.common.ResultFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * @author eleven
 * @date  2022-11-10 19:55:27
 * @description 全局异常处理器
 * Generated By: lesscoding.net basic service
 * Link to: <a href="https://lesscoding.net">https://lesscoding.net</a>
 * mail to:2496290990@qq.com zjh292411@gmail.com admin@lesscoding.net
 */
//@Component
//@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{


    //@ExceptionHandler({NotRoleException.class})
    //public Result notRoleExceptionHandler(NotRoleException e){
    //    log.error("====权限不足===",e);
    //    return ResultFactory.failed(e.getMessage());
    //}
    //@ExceptionHandler({NotLoginException.class})
    //public Result notLoginExceptionHandler(NotLoginException e) {
    //    log.error("======未登录访问登录资源======");
    //    return ResultFactory.failed(e.getMessage());
    //}
    /**
     * 处理运行时异常
     * @param   e       运行时异常对象
     * @return  Result  通用返回结果类
     */
    @ExceptionHandler({RuntimeException.class})
    public Result runtimeExceptionHandler(RuntimeException e){
        log.error("==服务异常==",e);
        return ResultFactory.exception(e);
    }

    /**
     * 处理运行时异常
     * @param   e       运行时异常对象
     * @return  Result  通用返回结果类
     */
    @ExceptionHandler({IOException.class})
    public Result ioExceptionHandler(IOException e){
        log.error("==文件不存在==",e);
        return ResultFactory.exception(e);
    }

    /**
     * 拦截异常基类
     * @param e
     * @return
     */
    //@ExceptionHandler
    //public SaResult handlerException(Exception e) {
    //    e.printStackTrace();
    //    return SaResult.error(e.getMessage());
    //}
}
