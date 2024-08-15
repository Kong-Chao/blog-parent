package com.sky.common.core.exception.handler;

import com.sky.common.core.domain.CommonResult;
import com.sky.common.core.exception.ErrorCode;
import com.sky.common.core.exception.ServerException;
import com.sky.common.core.exception.ServiceException;
import com.sky.common.core.exception.enums.GlobalErrorCodeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.stream.Collectors;

/** 全局异常处理器
 * @author admin
 */
/*@RestControllerAdvice*/
@Slf4j
public class GlobalExceptionHandler {

  /*  @ExceptionHandler(MethodArgumentNotValidException.class)*/
    public CommonResult<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        String messages = bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("；"));
        return CommonResult.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), messages);
    }

  /*  @ExceptionHandler(value = BindException.class)*/
    public CommonResult<String> exceptionHandler(BindException e) {
        log.error(e.getMessage());
        String messages = e.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("；"));
        return CommonResult.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), messages);
    }

  /*  @ExceptionHandler({Exception.class})*/
    public CommonResult<ErrorCode> defaultExceptionHandler(Exception e) {
        log.error(e.getMessage());
        return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
    }

   /* @ExceptionHandler({Throwable.class})*/
    public CommonResult<GlobalErrorCodeConstants> errorHandler(Throwable e) {
        log.error(e.getMessage());
        return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
    }

  /*  @ExceptionHandler(ServerException.class)*/
    public CommonResult<String> handleServerException(ServerException e) {
        log.error("服务器错误: {}", e.getMessage());
        return CommonResult.error(e.getCode(),e.getMessage());
    }

   /* @ExceptionHandler(ServiceException.class)*/
    public CommonResult<String> handleServiceException(ServiceException e) {
        log.error("业务错误: {}", e.getMessage());
        return CommonResult.error(e.getCode(),e.getMessage());
    }
}
