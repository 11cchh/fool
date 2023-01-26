package com.hangzhou.processor;

import com.hangzhou.spring.annotation.Component;
import com.hangzhou.test.circular.ClassA;
import com.hangzhou.test.proxy.JdkDynamicProxy;

/**
 * @Author Faye
 * @Date 2023/1/23 10:47
 */
@Component
public class JdkProxyBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {
    public Object getEarlyBeanReference(Object bean, String beanName) {
        if (bean instanceof ClassA) {
            JdkDynamicProxy jdkDynamicProxy = new JdkDynamicProxy(bean);
            return jdkDynamicProxy;
        }
        return bean;
    }
}
