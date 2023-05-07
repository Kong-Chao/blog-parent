package com.sky.business.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author kc
 * @create 2023-05-06 19:45
 *
 * 统一返回前端数据
 */

@Data
@AllArgsConstructor
public class Result {

    /**
     * 返回数据是否成功
     */
    private boolean success;

    /**
     *  状态码
     */
    private static int code;

    /**
     *  信息
     */
    private static String msg;

    /**
     *  返回的数据
     */
    private static Object data;

    public Result() {
    }

    public Result(boolean b, int code, String success, Object data) {
    }

    /**
     * 数据返回成功
     */
    public static Result success(Object data){
        return new Result(true,200,"操作成功", data);
    }

    /**
     * 数据返回失败
     */
    public static Result fail(int code, String msg){
        return new Result(false,code,msg,null);
    }

}
