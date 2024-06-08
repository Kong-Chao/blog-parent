package com.sky.common.utils;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class UUIDutil {
    // 生成UUID
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    // 生成加密安全的随机字符串
    public static String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
