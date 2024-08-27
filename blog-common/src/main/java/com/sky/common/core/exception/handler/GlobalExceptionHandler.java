package com.sky.common.core.exception.handler;

import com.sky.common.core.domain.CommonResult;
import com.sky.common.core.exception.ServiceException;
import com.sky.common.core.exception.enums.GlobalErrorCodeConstants;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * @author admin
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数校验异常: {}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        String messages = bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("；"));
        return CommonResult.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), messages);
    }

    @ExceptionHandler(BindException.class)
    public CommonResult<String> handleBindException(BindException e) {
        log.error("绑定异常: {}", e.getMessage());
        String messages = e.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("；"));
        return CommonResult.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), messages);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public CommonResult<String> handleExpiredJwtException(ExpiredJwtException ex) {
        log.error("JWT过期: {}", ex.getMessage());
        return CommonResult.error(GlobalErrorCodeConstants.UNAUTHORIZED.getCode(), "令牌已过期,请重新登录。");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public CommonResult<String> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',权限校验失败'{}'", requestURI, ex.getMessage());
        return CommonResult.error(GlobalErrorCodeConstants.FORBIDDEN.getCode(), "没有权限,无法访问此资源,请联系管理员授权。");
    }


    @ExceptionHandler(ServiceException.class)
    public CommonResult<String> handleServiceException(ServiceException e) {
        log.error("业务错误: {}", e.getMessage());
        return CommonResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public CommonResult<String> handleException(Exception ex) {
        log.error("未知错误: {}", ex.getMessage());
        return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode(), "发生意外错误。请稍后再试。");
    }

    @ExceptionHandler(Throwable.class)
    public CommonResult<String> handleThrowable(Throwable e) {
        log.error("系统错误: {}", e.getMessage());
        return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode(), "系统错误。处理步骤请联系技术支持。");
    }
}
