package com.sky.system.service;

import com.sky.api.login.vo.AuthLoginVO;
import com.sky.common.core.domain.enyity.SysUser;

import javax.validation.Valid;

/**
 * 登录认证接口
 */
public interface AuthService {

    AuthLoginVO login(@Valid AuthLoginVO authLoginVO);

    /**
     * 用户名+密码登录
     */
    SysUser authenticate(String username, String password);
}
