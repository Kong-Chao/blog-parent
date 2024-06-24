package com.sky.system.service.impi;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.sky.api.login.vo.AuthLoginVO;
import com.sky.api.login.vo.TokenVO;
import com.sky.common.constant.Constants;
import com.sky.common.core.domain.model.LoginUser;
import com.sky.common.core.exception.enums.GlobalErrorCodeConstants;
import com.sky.common.core.exception.util.ServiceExceptionUtil;
import com.sky.common.utils.UUIDutil;
import com.sky.common.utils.jwt.JwtTokenUtil;
import com.sky.framework.core.redis.service.RedisService;
import com.sky.system.service.AuthService;
import com.sky.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author kc
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    /**
     * 身份验证管理器
     */
    private AuthenticationManager authenticationManager;

    /**
     * 验证码服务
     */
    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisService redisService;

    @Override
    public TokenVO login(AuthLoginVO authLoginVO) {
        // 验证码校验
        //validateCaptcha(authLoginVO);
        // 账号密码登录验证
        LoginUser loginUser = authenticate(authLoginVO.getUsername(), authLoginVO.getPassword());
        Map<String, Object> claims = new HashMap<>(6);
        String uuid = UUIDutil.generateUUID();
        claims.put("uuid",uuid);
        claims.put("userId",loginUser.getUserId());
        claims.put("userName",authLoginVO.getUsername());
        // 创建token
        String accessToken = jwtTokenUtil.generateToken(claims);
        String refreshToken = jwtTokenUtil.generateRefreshToken(accessToken);
        long expiresIn = jwtTokenUtil.getExpirationDateFromToken(accessToken).getTime();
        // 缓存用户信息
        redisService.setCacheObject(Constants.AUTH_TOKEN + uuid,loginUser,2 * jwtTokenUtil.getExpirationTime(), TimeUnit.MILLISECONDS);
        TokenVO tokenVO = new TokenVO();
        tokenVO.setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .setExpiresIn(expiresIn);
        return tokenVO;
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
    public LoginUser authenticate(String username, String password) {
        // 手动用户验证
        Authentication authenticate = null;
        try{
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
            // 该方法会去调用CustomUserDetailsService.loadUserByUsername
            authenticate = authenticationManager.authenticate(token);
        }catch (BadCredentialsException e){
            throw new BadCredentialsException(GlobalErrorCodeConstants.ACCOUNT_PASSWORD_ERROR.getMsg());
        }catch (Exception e){
            throw ServiceExceptionUtil.exception(GlobalErrorCodeConstants.ACCOUNT_PASSWORD_ERROR);
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        return loginUser;
    }

    @Override
    public TokenVO refreshToken() {
        return null;
    }
}
