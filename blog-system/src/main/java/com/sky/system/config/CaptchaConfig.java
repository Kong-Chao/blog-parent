package com.sky.system.config;

import com.anji.captcha.properties.AjCaptchaProperties;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.impl.CaptchaServiceFactory;
import com.sky.system.service.CustomCaptchaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 验证码配置类
 */
@Configuration
public class CaptchaConfig {

    @Bean
    public CaptchaCacheService captchaCacheService(AjCaptchaProperties config,
                                                   StringRedisTemplate stringRedisTemplate) {
        CaptchaCacheService captchaCacheService = CaptchaServiceFactory.getCache(config.getCacheType().name());
        if (captchaCacheService instanceof CustomCaptchaService) {
            ((CustomCaptchaService) captchaCacheService).setStringRedisTemplate(stringRedisTemplate);
        }
        return captchaCacheService;
    }

}
