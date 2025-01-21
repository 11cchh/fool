package com.hangzhou.exception;

import com.hangzhou.constant.ResultEnum;
import lombok.Data;

/**
 * 统一异常处理
 *
 * @Author: Faye
 * @Data: 2022/9/14 11:17
 */
@Data
public class BizException extends RuntimeException {
    private Integer code;
    private String message;

    /**
     * 给自定义业务异常类用的方法
     *
     * @param resultEnum responseEnum
     */
    public BizException(ResultEnum resultEnum) {
        this(resultEnum.getCode(), resultEnum.getMessage());
    }

    public BizException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
