package com.hangzhou.constant;

import lombok.Data;
import lombok.Getter;

/**
 * @Author: Faye
 * @Data: 2022/9/14 11:18
 */
@Getter
public enum ResultEnum {
    /**
     * success
     */
    SUCCESS(200,"成功"),
    /**
     * error
     */
    ERROR(99,"失败");

    private final Integer code;
    private final String message;

    ResultEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
