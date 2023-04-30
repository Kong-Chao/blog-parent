package com.sky.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author kc
 * @create 2023-04-30 15:57
 */
@Configuration
@MapperScan("com.sky.mapper")
public class MyBatisPlusConfig {
}
