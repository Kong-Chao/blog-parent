package com.sky.common.core.exception;

import com.sky.common.core.exception.enums.ServiceErrorCodeRange;
import lombok.Getter;

/**
 * 业务逻辑异常 Exception
 * @author admin
 */
@Getter
public final class ServiceException extends BaseException {

    /**
     * 业务错误码
     *
     * @see ServiceErrorCodeRange
     */
    private int code;
    /**
     * 错误提示
     */
    private String message;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException() {super("认证失败!");}

    public ServiceException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMsg();
    }

    public ServiceException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
