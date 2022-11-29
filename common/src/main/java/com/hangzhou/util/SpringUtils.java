package com.hangzhou.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @Author: Faye
 * @Data: 2022/9/14 11:14
 */
@Component
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (context == null) {
            context = applicationContext;
        }
    }

    /**
     * 获取当前环境
     *
     * @return String
     */
    public static String getActiveProfile() {
        return context.getEnvironment().getActiveProfiles()[0];
    }

    /**
     * 获取applicationContext上下文
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    /**
     * 通过name获取 Bean
     *
     * @param name name
     * @return Object
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);

    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz clazz
     * @param <T>   <T>
     * @return <T>
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name  name
     * @param clazz clazz
     * @param <T>   <T>
     * @return <T>
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 通过type获取Bean
     *
     * @param clazz clazz
     * @param <T>   <T>
     * @return <T>
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return getApplicationContext().getBeansOfType(clazz);
    }


    /**
     * 获取有关注解的bean
     *
     * @param clazz
     * @return
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> clazz) {
        return getApplicationContext().getBeansWithAnnotation(clazz);
    }

    /**
     * 移除bean
     *
     * @param beanId bean的id
     */
    public static void unregisterBean(String beanId) {
        ConfigurableApplicationContext configurableContext = (ConfigurableApplicationContext) context;
        BeanDefinitionRegistry beanDefinitionRegistry = (DefaultListableBeanFactory) configurableContext.getBeanFactory();
        beanDefinitionRegistry.removeBeanDefinition(beanId);
    }

    /**
     * 注册bean
     *
     * @param beanId    所注册bean的id
     * @param className bean的className，
     *                  三种获取方式：1、直接书写，如：com.mvc.entity.User
     *                  2、User.class.getName
     *                  3.user.getClass().getName()
     */
    public static void registerBean(String beanId, String className) {
        BeanDefinitionBuilder beanDefinitionBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(className);
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        ConfigurableApplicationContext configurableContext = (ConfigurableApplicationContext) context;
        BeanDefinitionRegistry beanDefinitionRegistry = (DefaultListableBeanFactory) configurableContext.getBeanFactory();
        beanDefinitionRegistry.registerBeanDefinition(beanId, beanDefinition);
    }
}
