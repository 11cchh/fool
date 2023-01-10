package com.hangzhou.spring.beandefinition;

/**
 * @Author Faye
 * @Date 2023/1/7 15:04
 */
public class BeanDefinitionReaderUtils {
    /**
     * 注册 beanDefinition
     *
     * @param beanDefinition 对象定义
     * @param registry       注册器
     */
    public static void registerBeanDefinition(AnnotatedBeanDefinition beanDefinition,
                                              BeanDefinitionRegistry registry) {
        String beanName = ((AnnotatedGenericBeanDefinition)beanDefinition).getClazz().getSimpleName();
        registry.registerBeanDefinition(beanName, beanDefinition);
    }
}
