package com.sky.api.login.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TokenVO {
    // 访问令牌
    private String accessToken;

    // 刷新令牌
    private String refreshToken;

    // token有效期
    private Long expiresIn;
}
