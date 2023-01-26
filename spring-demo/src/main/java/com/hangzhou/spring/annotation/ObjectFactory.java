package com.hangzhou.spring.annotation;

/**
 * @Author Faye
 * @Date 2023/1/23 12:44
 */
@FunctionalInterface
public interface ObjectFactory<T> {
    T getObject();
}
