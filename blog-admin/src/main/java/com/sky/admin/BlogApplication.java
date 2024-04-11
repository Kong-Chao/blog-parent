package com.sky.admin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author kc
 * @create 2023-04-29 15:28
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class BlogApplication {
    public static void main(String[] args) {
       SpringApplication.run(BlogApplication.class,args);
    }
}
