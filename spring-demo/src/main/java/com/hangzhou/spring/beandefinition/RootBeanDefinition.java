package com.hangzhou.spring.beandefinition;

/**
 * @Author Faye
 * @Date 2023/1/22 15:52
 */
public class RootBeanDefinition implements BeanDefinition {
    private Object beanClass;

    public RootBeanDefinition(Class<?> beanClass) {
        setBeanClass(beanClass);
    }

    public RootBeanDefinition() {
    }

    private void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public Class<?> getBeanClass() {
        Object beanClassObject = this.beanClass;
        return (Class<?>) beanClassObject;
    }
}
