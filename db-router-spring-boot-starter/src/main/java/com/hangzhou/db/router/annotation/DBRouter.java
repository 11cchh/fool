package com.hangzhou.db.router.annotation;

import java.lang.annotation.*;

/**
 * 路由注解
 *
 * @Author Faye
 * @Date 2023/12/22 16:35
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DBRouter {
    /**
     * 分库分表字段
     *
     * @return String
     */
    String key() default "";
}
