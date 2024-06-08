package com.sky.system.controller.captcha;

import cn.hutool.core.util.StrUtil;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.sky.common.utils.servlet.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;

/**
 * @author kc
 * 验证码
 */
@RestController
@RequestMapping("/system/captcha")
public class SystemCaptchaController {

    @Autowired
    private CaptchaService captchaService;

    @GetMapping("/test")
    public String test(){
        return "Hello Word";
    }

    /**
     * 获取验证码
     * @param captchaVO
     * @param request
     * @return
     */
    @PermitAll
    @PostMapping("/get")
    public ResponseModel get(@RequestBody CaptchaVO captchaVO , HttpServletRequest request){
        assert request.getRemoteHost() != null;
        captchaVO.setBrowserInfo(getReomteId(request));
        return captchaService.get(captchaVO);
    }

    /**
     * 核验验证码
     * @param data
     * @param request
     * @return
     */
    @PermitAll
    @PostMapping("/check")
    public ResponseModel check(@RequestBody CaptchaVO data, HttpServletRequest request) {
        data.setBrowserInfo(getReomteId(request));
        return captchaService.check(data);
    }

    public static String getReomteId(HttpServletRequest request){
        String ip = ServletUtils.getClientIP(request);
        String ua = request.getHeader("user-agent");
        if (StrUtil.isNotBlank(ip)) {
            return ip + ua;
        }
        return request.getRemoteAddr() + ua;
    }
}
