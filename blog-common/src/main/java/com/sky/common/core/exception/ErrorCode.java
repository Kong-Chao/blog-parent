package com.sky.common.core.exception;

import com.sky.common.core.exception.enums.GlobalErrorCodeConstants;
import com.sky.common.core.exception.enums.ServiceErrorCodeRange;
import lombok.Data;

/**
 * 错误码对象
 *
 * 全局错误码，占用 [0, 999], 参见 {@link GlobalErrorCodeConstants}
 * 业务异常错误码，占用 [1 000 000 000, +∞)，参见 {@link ServiceErrorCodeRange}
 *
 * TODO 错误码设计成对象的原因，为未来的 i18 国际化做准备
 * @author admin
 */
@Data
public class ErrorCode {

    /**
     * 错误码
     */
    private final int code;
    /**
     * 错误提示
     */
    private final String msg;

    public ErrorCode(int code, String message) {
        this.code = code;
        this.msg = message;
    }

}
