package com.hangzhou.spring.applicationcontext;

import com.hangzhou.spring.beandefinition.AnnotatedBeanDefinitionReader;
import com.hangzhou.spring.beandefinition.BeanDefinitionRegistry;

/**
 * 基于注解的容器
 *
 * @Author Faye
 * @Date 2023/1/7 14:25
 */
public class AnnotationConfigApplicationContext extends GenericApplicationContext implements BeanDefinitionRegistry {

    private AnnotatedBeanDefinitionReader reader;

    /**
     * 如果有人调用该无参构造器，必须调用父类的无参构造
     * 父类初始化 DefaultListableBeanFactory
     */
    public AnnotationConfigApplicationContext() {
        this.reader = new AnnotatedBeanDefinitionReader(this);
    }

    public AnnotationConfigApplicationContext(Class<?> componentClass) {
        // 1. 读取 componentClass 扫描路径所在的类
        // 定义一个阅读器，专门读取 AnnotatedBeanDefinitionReader
        this();

        // 2. 将这个类解析成 BeanDefinition 并注册（registerBeanDefinition）到 bean 工厂里
        register(componentClass);

        // 3. 扫描路径，然后提取出路径下所有的 bean 注册到 bean 工厂当中（单例 bean 的初始化）
        // refresh 核心方法定义在父类当中，让所有子类都能使用这个方法
        refresh();
    }

    private void register(Class<?> componentClass) {
        this.reader.register(componentClass);
    }
}
