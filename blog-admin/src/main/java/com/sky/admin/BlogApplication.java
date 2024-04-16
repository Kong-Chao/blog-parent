package com.sky.admin;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author kc
 * @create 2023-04-29 15:28
 */
@SpringBootApplication(scanBasePackages = {"com.sky"})
@MapperScan("com.sky.**.mapper")
public class BlogApplication {
    public static void main(String[] args) {
       SpringApplication.run(BlogApplication.class,args);
    }
}
