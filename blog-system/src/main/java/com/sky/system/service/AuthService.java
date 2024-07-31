package com.sky.system.service;

import com.sky.api.login.vo.AuthLoginVO;
import com.sky.api.login.vo.RefreshTokenVO;
import com.sky.api.login.vo.TokenVO;
import com.sky.common.core.domain.UserBO;
import com.sky.common.core.domain.entity.SysUser;

import javax.validation.Valid;

/**
 * 登录认证接口
 * @author admin
 */
public interface AuthService {

    TokenVO login(@Valid AuthLoginVO authLoginVO);

    /**
     * 用户名+密码登录
     */
    UserBO authenticate(String username, String password);

    TokenVO refreshToken(RefreshTokenVO refreshTokenVO);

    SysUser getUserInfo();
}
