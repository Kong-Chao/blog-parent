package com.sky.api.login.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author kc
 */
@Data
public class RefreshTokenVO {

    @NotEmpty(message = "刷新token不能为空")
    String refreshToken;
}
