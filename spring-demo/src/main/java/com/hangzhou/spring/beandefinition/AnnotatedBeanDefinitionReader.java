package com.hangzhou.spring.beandefinition;

import com.hangzhou.spring.annotation.Scope;

/**
 * @Author Faye
 * @Date 2023/1/7 14:42
 */
public class AnnotatedBeanDefinitionReader {
    /**
     * 注册器
     */
    private BeanDefinitionRegistry registry;

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * 注册路径扫描的 bean 到 bean 工厂里
     * @param componentClass
     */
    public void register(Class<?> componentClass) {
        registerBean(componentClass);
    }

    private void registerBean(Class<?> componentClass) {
        doRegisterBean(componentClass);
    }

    private void doRegisterBean(Class<?> componentClass) {
        // 把 appConfig 读取成一个 BeanDefinition 定义
        AnnotatedGenericBeanDefinition beanDefinition =
                new AnnotatedGenericBeanDefinition();
        beanDefinition.setClazz(componentClass);
        if (componentClass.isAnnotationPresent(Scope.class)) {
            String scope = componentClass.getAnnotation(Scope.class).value();
            beanDefinition.setScope(scope);
        } else {
            // 默认单例模式
            beanDefinition.setScope("singleton");
        }

        // BeanDefinition 创建完成后，给 BeanFactory 进行 Bean 注册
        BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinition, this.registry);
    }
}
