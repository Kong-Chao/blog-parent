package com.sky.common.core.exception;

/**
 * 通用异常类
 * @author admin
 */
public class BaseException extends RuntimeException {

    public BaseException() {super("系统异常！");}

    public BaseException(String message) {super(message);}
}
