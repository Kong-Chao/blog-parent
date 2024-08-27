package com.sky.framework.secutilty.config;

import com.sky.framework.secutilty.handler.AccessDeniedHandlerImpl;
import com.sky.framework.secutilty.handler.AuthenticationEntryPointImpl;
import com.sky.framework.secutilty.handler.LogoutSuccessHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * SpringSecurity 安全配置
 * @author admin
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityAutoConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     *  Spring Security 加密器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(securityProperties.getPasswordEncoderLength());
    }

    /**
     * 认证失败处理类 Bean
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new AuthenticationEntryPointImpl();
    }

    /**
     * 权限不够处理器 Bean
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }

    /**
     * 身份验证管理器
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 登录退出类
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new LogoutSuccessHandlerImpl();
    }
}
