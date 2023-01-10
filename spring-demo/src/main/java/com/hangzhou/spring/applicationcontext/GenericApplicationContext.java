package com.hangzhou.spring.applicationcontext;

import com.hangzhou.spring.beandefinition.AnnotatedBeanDefinition;
import com.hangzhou.spring.beandefinition.BeanDefinitionRegistry;
import com.hangzhou.spring.beanfactory.DefaultListableBeanFactory;

/**
 * @Author Faye
 * @Date 2023/1/9 17:38
 */
public class GenericApplicationContext implements BeanDefinitionRegistry {
    /**
     * beanFactory 放在 AnnotationConfigApplicationContext 类中初始化其实也可以
     */
    private DefaultListableBeanFactory beanFactory;

    public GenericApplicationContext() {
        this.beanFactory = new DefaultListableBeanFactory();
    }

    @Override
    public void registerBeanDefinition(String beanName, AnnotatedBeanDefinition beanDefinition) {
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    public void refresh() {
        // 获取 bean 工厂
        DefaultListableBeanFactory beanFactory = obtainBeanFactory();

        // 把 AppConfig 类注解路径下的所有 bean 进行扫描并注册到 bean 工厂里
        invokeBeanFactoryPostProcessors(beanFactory);

        // 初始化 beanDefinition 成单例 bean，放到单例bean对应的容器（缓存）里
        finishBeanFactoryInitialization(beanFactory);
    }

    private void invokeBeanFactoryPostProcessors(DefaultListableBeanFactory beanFactory) {
        // 简化，doScan 方法并没有在 beanFactory 里
        beanFactory.doScan();
    }

    private void finishBeanFactoryInitialization(DefaultListableBeanFactory beanFactory) {
        beanFactory.preInstantiateSingletons();
    }

    private DefaultListableBeanFactory obtainBeanFactory() {
        return this.beanFactory;
    }

    /**
     * 获取对象
     * @param beanName 对象名称
     */
    public Object getBean(String beanName) {
        return this.beanFactory.getBean(beanName);
    }
}
