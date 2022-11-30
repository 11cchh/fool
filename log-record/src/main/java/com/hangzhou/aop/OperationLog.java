package com.hangzhou.aop;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @Author Faye
 * @Date 2022/11/29 17:05
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {
    /**
     * 记录操作描述
     *
     * @return 操作描述
     */
    String desc() default "";

    Class<? extends Convert> convert();
}
