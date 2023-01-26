package com.hangzhou.test;

import com.hangzhou.processor.JdkProxyBeanPostProcessor;
import com.hangzhou.spring.annotation.Autowired;
import com.hangzhou.spring.annotation.ObjectFactory;
import com.hangzhou.spring.beandefinition.BeanDefinition;
import com.hangzhou.spring.beandefinition.RootBeanDefinition;
import com.hangzhou.test.circular.ClassA;
import com.hangzhou.test.circular.ClassB;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author Faye
 * @Date 2023/1/22 16:40
 */
public class TestCircularDependencies {
    /**
     * 存放 BeanDefinition
     */
    private static Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * 一级缓存，存放完整的 bean 对象
     */
    private static Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    /**
     * 二级缓存，存放不完整的 bean 对象
     * 如果只有一个一级缓存的存在，那么一级缓存中会既有 完整的普通对象 和 存在循环依赖的不完整的对象，不合理
     */
    private static Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();

    /**
     * 三级缓存
     * 当类中存在方法增强时，循环依赖类中的的依赖应该拿到每次动态代理生成的类
     * 但 Spring 中生命周期是：实例化 -> 属性填充 -> 初始化 -> aop
     * 因为 aop 的位置过于靠后，没办法干预到属性填充的过重所以引入三级缓存起到一个延迟加载的功能
     */
    private static Map<String, ObjectFactory> singletonFactories = new ConcurrentHashMap<>();

    /**
     * 当前循环依赖的 bean 是否正在创建中
     */
    private static Set<String> singletonCurrentlyInCreation = new HashSet<>();

    public static void loadBeanDefinitions() {
        RootBeanDefinition abd = new RootBeanDefinition(ClassA.class);
        RootBeanDefinition bbd = new RootBeanDefinition(ClassB.class);
        beanDefinitionMap.put("classA", abd);
        beanDefinitionMap.put("classB", bbd);
    }

    // 循环依赖不能解决构造器和原型模式的循环依赖
    public static void main(String[] args) throws Exception{
        loadBeanDefinitions();
        for (String beanName : beanDefinitionMap.keySet()) {
            getBean(beanName);
        }
        ClassA a = (ClassA) getBean("classA");
        a.execute();
    }

    private static Object getBean(String beanName) throws Exception {
        Object singleton = getSingleton(beanName);
        if (Objects.nonNull(singleton)) {
            return singleton;
        }

        if (!singletonCurrentlyInCreation.contains(beanName)) {
            singletonCurrentlyInCreation.add(beanName);
        }

        // Bean 实例化（通过对象的构造函数或者反射进行对象的创建）
        RootBeanDefinition bd = (RootBeanDefinition) beanDefinitionMap.get(beanName);
        Class<?> clazz = bd.getBeanClass();

        // 无参构造
        Object instance = clazz.newInstance();

        // 存放不完整的对象到二级缓存当中
        earlySingletonObjects.put(beanName, instance);

        // 如果有循环依赖并且有 aop 代理
        if (singletonCurrentlyInCreation.contains(beanName)) {
            singletonFactories.put(beanName,
                    () -> new JdkProxyBeanPostProcessor()
                            .getEarlyBeanReference(earlySingletonObjects.get(beanName), beanName));
        }

        // 属性填充
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Autowired annotation = field.getAnnotation(Autowired.class);
            if (Objects.nonNull(annotation)) {
                field.setAccessible(true);
                String name = field.getName();
                Object dependency = getBean(name);
                field.set(instance, dependency);
            }
        }

        // 初始化（调用一些 init method）

        // 方法增强（AOP）

        // 放入一级缓存中，二级、三级缓存清除
        singletonObjects.put(beanName, instance);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
        return instance;
    }

    private static Object getSingleton(String beanName) {
        Object object = singletonObjects.get(beanName);
        if (Objects.isNull(object) && singletonCurrentlyInCreation.contains(beanName)) {
            object = earlySingletonObjects.get(beanName);
            if (Objects.isNull(object)) {
                ObjectFactory factory = singletonFactories.get(beanName);
                if (Objects.nonNull(factory)) {
                    object = factory.getObject();
                    // 替换二级缓存中的对象为代理对象
                    earlySingletonObjects.put(beanName, object);
                    singletonFactories.remove(beanName);
                }
            }
        }
        return object;
    }
}
