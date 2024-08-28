package com.sky.common.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sky.common.constant.HttpStatus;
import com.sky.common.core.exception.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 * @author admin
 */
@Data
public class CommonResult<T> implements Serializable {

    /**
     * 状态码
     */
    private int code;

    /**
     * 返回数据
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 数据返回消息
     */
    private String msg;

    /**
     * 是否成功
     */
    private boolean success;

    private static final int SUCCESS = HttpStatus.SUCCESS;
    private static final int FAIL = HttpStatus.ERROR;

    /**
     * 私有化构造方法，确保对象只能通过工厂方法创建
     */
    private CommonResult(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = code == SUCCESS;
    }

    private CommonResult(int code, String msg) {
        this(code, null, msg);
    }

    /**
     * 错误返回
     */
    public static <T> CommonResult<T> error() {
        return new CommonResult<>(FAIL, "操作失败");
    }

    public static <T> CommonResult<T> error(ErrorCode errorCode) {
        return new CommonResult<>(errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> CommonResult<T> error(String msg) {
        return new CommonResult<>(FAIL, msg);
    }

    public static <T> CommonResult<T> error(T data) {
        return new CommonResult<>(FAIL, data, "操作失败");
    }

    public static <T> CommonResult<T> error(int code, String msg) {
        return new CommonResult<>(code, msg);
    }

    /**
     * 成功返回
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<>(SUCCESS, "操作成功");
    }

    public static <T> CommonResult<T> success(String msg) {
        return new CommonResult<>(SUCCESS, msg);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(SUCCESS, data, "操作成功");
    }

    public static <T> CommonResult<T> success(T data, String msg) {
        return new CommonResult<>(SUCCESS, data, msg);
    }
}
