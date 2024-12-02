package com.sky.system.controller.auth;

import com.sky.api.login.vo.AuthLoginVO;
import com.sky.api.login.vo.RefreshTokenVO;
import com.sky.api.login.vo.TokenVO;
import com.sky.common.core.domain.CommonResult;
import com.sky.common.core.domain.entity.SysUser;
import com.sky.common.core.domain.vo.RouterVO;
import com.sky.system.service.AuthService;
import com.sky.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 认证接口
 * @author admin
 */
@RestController
@RequestMapping("/system/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private SysMenuService sysMenuService;

    @PostMapping("/login")
    public CommonResult<TokenVO> login(@RequestBody @Valid AuthLoginVO authLoginVO){
        return CommonResult.success(authService.login(authLoginVO));
    }

    @PostMapping("/refresh")
    public CommonResult<TokenVO> refresh(@RequestBody @Valid RefreshTokenVO refreshTokenVO){
        return authService.refreshToken(refreshTokenVO);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/getInfo")
    public CommonResult<SysUser> getInfo(){
        return CommonResult.success(authService.getUserInfo());
    }

    /**
     * 根据用户id获取路由
     * @return
     */
    @GetMapping("/getRoutes")
    public CommonResult<List<RouterVO>> getRoutes(){
        return CommonResult.success(sysMenuService.getRoutes());
    }

}
