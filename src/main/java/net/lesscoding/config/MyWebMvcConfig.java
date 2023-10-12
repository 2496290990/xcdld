package net.lesscoding.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author eleven
 * @date 2022/11/12 16:28
 * @apiNote
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(1800)
                .allowedOrigins("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor(handle -> {
                    SaRouter
                            .match("/**")    // 拦截的 path 列表，可以写多个 */
                            .notMatch("/sys/**")//系统服务全排除
                            .notMatch("/battle/**")//系统服务全排除
                            // 下边的是knife4j使用的
                            .notMatch("/*.html")
                            .notMatch("/swagger-resources")
                            .notMatch("/webjars/**")
                            .notMatch("/**/api-docs")
                            .notMatch("/favicon.ico")
                            .notMatch("/file/qiniu")
                            .check(r -> StpUtil.checkLogin());
                }))
                .addPathPatterns("/**")
        ;
    }

    /**
     * 添加分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //如果配置多个插件,切记分页最后添加
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 如果有多数据源可以不配具体类型 否则都建议配上具体的DbType
        //interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

}