package com.sky.system.service.impi;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.sky.api.login.vo.AuthLoginVO;
import com.sky.common.constant.ErrorCodeConstants;
import com.sky.common.core.domain.enyity.SysUser;
import com.sky.common.core.exception.util.ServiceExceptionUtil;
import com.sky.system.service.AuthService;
import com.sky.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kc
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 验证码服务
     */
    @Autowired
    private CaptchaService captchaService;

    @Override
    public AuthLoginVO login(AuthLoginVO authLoginVO) {
        // 验证码校验
        validateCaptcha(authLoginVO);
        // 账号密码登录验证
        SysUser sysUser = authenticate(authLoginVO.getUsername(), authLoginVO.getPassword());
        // 创建 Token 令牌，记录登录日志

        return null;
    }

    private void validateCaptcha(AuthLoginVO authLoginVO) {
        // 验证码校验
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(authLoginVO.getCaptchaVerification());
        ResponseModel re = captchaService.verification(captchaVO);
       /* if (!re.isSuccess()){
            throw new BaseException(GlobalErrorCodeEnum.INTERNAL_SERVER_ERROR.getCode(),"验证码错误，原因:" + "{" + re.getRepMsg() +  "}");
        }*/
    }

    /**
     * 用户名+密码登录
     */
    @Override
    public SysUser authenticate(String username, String password) {
        // 校验账号是否存在
        SysUser sysUser = sysUserService.selectByUserName(username);
        if (sysUser == null){
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS);
        }
        return sysUser;
    }
}
