package com.sky.framework.secutilty.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

/**
 *  SpringSecurity 安全配置类
 * @author kc
 */
@ConfigurationProperties(prefix = "blog.security")
@Data
public class SecurityProperties {

    /**
     * 白名单URL
     */
    private List<String> whiteList = Collections.emptyList();

    /**
     * PasswordEncoder 加密复杂度，越高开销越大
     */
    private Integer passwordEncoderLength = 4;
}
