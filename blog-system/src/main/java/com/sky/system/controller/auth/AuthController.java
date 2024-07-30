package com.sky.system.controller.auth;

import com.sky.api.login.vo.AuthLoginVO;
import com.sky.api.login.vo.RefreshTokenVO;
import com.sky.api.login.vo.TokenVO;
import com.sky.common.core.domain.CommonResult;
import com.sky.common.core.domain.entity.UserBO;
import com.sky.system.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 认证接口
 * @author admin
 */
@RestController
@RequestMapping("/system/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public CommonResult<TokenVO> login(@RequestBody @Valid AuthLoginVO authLoginVO){
        return CommonResult.success(authService.login(authLoginVO));
    }

    @PostMapping("/refresh")
    public CommonResult<TokenVO> refresh(@RequestBody @Valid RefreshTokenVO refreshTokenVO){
        return CommonResult.success(authService.refreshToken(refreshTokenVO));
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/getInfo")
    public CommonResult<UserBO> getInfo(){
        return CommonResult.success(authService.getUserInfo());
    }

}
