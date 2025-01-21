package com.hangzhou.base;

import com.hangzhou.constant.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: Faye
 * @Data: 2022/9/14 11:21
 */
@Data
@AllArgsConstructor
public class Result<T> {

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 返回结果信息
     */
    private T data;


    /**
     * 请求成功返回对象
     */
    public static <T> Result<T> success() {
        return success(null);
    }


    /**
     * @param data 返回的数据
     * @return 返回成功 包含code,msg,data
     */
    public static <T> Result<T> success(T data) {
        return success(ResultEnum.SUCCESS.getMessage(), data);
    }

    /**
     * @param data 返回的数据
     * @param msg  提示信息
     * @return 返回成功 包含code,msg,data
     */
    public static <T> Result<T> success(String msg, T data) {
        return success(ResultEnum.SUCCESS.getCode(), msg, data);
    }

    /**
     * @param code 响应码
     * @param msg  提示信息
     * @param data 返回数据
     * @return 返回成功 包含code,msg,data
     */
    public static <T> Result<T> success(Integer code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    /**
     * @return 返回失败
     */
    public static <T> Result<T> error() {
        return error(ResultEnum.ERROR.getMessage());
    }

    /**
     * @return 返回失败 包含msg
     */
    public static <T> Result<T> error(String msg) {
        return error(ResultEnum.ERROR.getCode(), msg);
    }

    /**
     * @param code 响应码
     * @param msg  提示信息
     * @return 返回失败 包含code,msg
     */
    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }
}
