package com.sky.common.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 全局异常获取处理
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public final class BaseException  extends RuntimeException{

    /**
     * 错误码
     */
    private Integer code;

    private String msg;

}
