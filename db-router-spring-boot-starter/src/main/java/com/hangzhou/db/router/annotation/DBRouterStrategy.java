package com.hangzhou.db.router.annotation;

import java.lang.annotation.*;

/**
 * 路由组建策略
 *
 * @Author Faye
 * @Date 2023/12/22 17:01
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DBRouterStrategy {
    /**
     * 是否开启分表
     *
     * @return boolean
     */
    boolean splitTable() default false;
}
