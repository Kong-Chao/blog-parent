package com.sky.common.core.exception.handler;

import com.sky.common.core.domain.CommonResult;
import com.sky.common.core.exception.ServerException;
import com.sky.common.core.exception.ServiceException;
import com.sky.common.core.exception.enums.GlobalErrorCodeConstants;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        return CommonResult.error(GlobalErrorCodeConstants.UNAUTHORIZED.getCode(), "令牌已过期。请重新登录。");
    }

    @ExceptionHandler(ServerException.class)
    public CommonResult<String> handleServerException(ServerException e) {
        log.error("服务器错误: {}", e.getMessage());
        return CommonResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public CommonResult<String> handleServiceException(ServiceException e) {
        log.error("业务错误: {}", e.getMessage());
        return CommonResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public CommonResult<String> handleException(Exception ex) {
        log.error("未知错误: {}", ex.getMessage());
        return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode(), "An unexpected error occurred. Please try again later.");
    }

    @ExceptionHandler(Throwable.class)
    public CommonResult<String> handleThrowable(Throwable e) {
        log.error("系统错误: {}", e.getMessage());
        return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode(), "A system error occurred. Please contact support.");
    }
}
