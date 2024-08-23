package com.sky.common.core.domain;

import com.sky.common.constant.HttpStatus;
import com.sky.common.core.exception.ErrorCode;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 通用返回类
 * @author kc
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
    private T data;

    /**
     * 数据返回消息
     */
    private String msg;

    private boolean success = isSuccess();

    public static final int SUCCESS = HttpStatus.SUCCESS;

    public static final int FAIL = HttpStatus.ERROR;

    public static <T> CommonResult<T> error(){
        return restResult(FAIL, "操作失败");
    }

    public static <T> CommonResult<T> error(ErrorCode errorCode){
        return restResult(errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> CommonResult<T> error(String msg){
        return restResult(FAIL, msg);
    }

    public static <T> CommonResult<T> error(T data){
        return restResult(FAIL,data, "操作失败");
    }

    public static <T> CommonResult<T> error(int code , String msg){
       return restResult(code, msg);
    }

    public static <T> CommonResult<T> success(){
        return restResult(SUCCESS,"操作成功");
    }

    public static <T> CommonResult<T> success(String msg){
        return restResult(SUCCESS,msg);
    }

    public static <T> CommonResult<T> success(T data){
        return restResult(SUCCESS,data,"操作成功");
    }

    public static <T> CommonResult<T> success(T data , String msg){
        return restResult(SUCCESS,data,msg);
    }

    private static <T> CommonResult<T> restResult(int code,T data, String msg){
        CommonResult<T> result = new CommonResult<>();
        result.code = code;
        result.msg = msg;
        result.data = data;
        return result;
    }

    private static <T> CommonResult<T> restResult(int code, String msg){
        CommonResult<T> result = new CommonResult<>();
        result.code = code;
        result.msg = msg;
        return result;
    }

    public boolean isSuccess(){
        return Objects.equals(this.code,SUCCESS);
    }
}
