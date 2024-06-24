package com.sky.system.service;

import com.sky.api.login.vo.AuthLoginVO;
import com.sky.api.login.vo.RefreshTokenVO;
import com.sky.api.login.vo.TokenVO;
import com.sky.common.core.domain.model.LoginUser;

import javax.validation.Valid;

/**
 * 登录认证接口
 */
public interface AuthService {

    TokenVO login(@Valid AuthLoginVO authLoginVO);

    /**
     * 用户名+密码登录
     */
    LoginUser authenticate(String username, String password);

    TokenVO refreshToken(RefreshTokenVO refreshTokenVO);
}
