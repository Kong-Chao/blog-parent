package com.sky.system.service;

import com.anji.captcha.service.CaptchaCacheService;
import lombok.Setter;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 验证码自定义接口
 */
@Setter
@Component
public class CustomCaptchaService implements CaptchaCacheService {

    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String type() {
        return "redis";
    }

    @Override
    public void set(String key, String value, long expiresInSeconds) {
        stringRedisTemplate.opsForValue().set(key, value, expiresInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean exists(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }

    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public Long increment(String key, long val) {
        return stringRedisTemplate.opsForValue().increment(key,val);
    }

}
