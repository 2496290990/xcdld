package net.lesscoding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.HashMap;

/**
 * @author eleven
 * @date 2022/11/12 14:42
 * @apiNote 跨域配置
 */
@Configuration
public class CrosConfig {

    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        HashMap<String,CorsConfiguration> corsMap = new HashMap<>();
        corsMap.put("/**",config);
        source.setCorsConfigurations(corsMap);
        return new CorsFilter(source);
    }
}
