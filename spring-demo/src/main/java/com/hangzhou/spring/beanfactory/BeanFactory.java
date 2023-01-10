package com.hangzhou.spring.beanfactory;

/**
 * @Author Faye
 * @Date 2023/1/9 11:29
 */
public interface BeanFactory {
    Object getBean(String beanName);
}
