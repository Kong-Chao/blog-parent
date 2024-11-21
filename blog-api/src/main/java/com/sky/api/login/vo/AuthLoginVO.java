package com.sky.api.login.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 用户登录访问字段
 * @author admin
 */
@Data
public class AuthLoginVO {

    // 账号
    @NotEmpty(message = "登录账号不能为空")
    @Length(min = 4,max = 16,message = "账号长度4-16位")
    @Pattern(regexp = "^[A-Za-z0-9]+$",message = "账号格式为数字以及字母")
    private String username;

    @NotEmpty(message = "密码不能为空")
    @Length(min = 4,max = 16,message = "账号长度4-16位")
    // 密码
    private String password;
}
