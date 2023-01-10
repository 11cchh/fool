package com.hangzhou.spring.beandefinition;

/**
 * @Author Faye
 * @Date 2023/1/7 15:02
 */
public interface BeanDefinitionRegistry {
    /**
     * 注册 BeanDefinition
     *
     * @param beanName       对象名称
     * @param beanDefinition 对象定义
     */
    void registerBeanDefinition(String beanName, AnnotatedBeanDefinition beanDefinition);
}
