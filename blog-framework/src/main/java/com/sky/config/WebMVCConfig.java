package com.sky.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author kc
 * @create 2023-04-30 17:01
 * 跨域配置
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 跨域配置 允许所有的8080端口请求访问到 8888后端接口
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");
    }
}
