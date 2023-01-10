package com.hangzhou.spring.beanfactory;

import com.hangzhou.spring.annotation.ComponentScan;
import com.hangzhou.spring.annotation.Scope;
import com.hangzhou.spring.annotation.Service;
import com.hangzhou.spring.beandefinition.AnnotatedBeanDefinition;
import com.hangzhou.spring.beandefinition.AnnotatedGenericBeanDefinition;
import com.hangzhou.spring.beandefinition.BeanDefinitionRegistry;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Faye
 * @Date 2023/1/9 11:31
 */
public class DefaultListableBeanFactory implements BeanFactory, BeanDefinitionRegistry {
    /**
     * BeanDefinition 缓存
     */
    private final Map<String, AnnotatedBeanDefinition> beanDefinitionMap =
            new ConcurrentHashMap(256);

    /**
     * bean 名称
     */
    private List<String> beanDefinitionNames = new ArrayList<>();

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap(256);

    /**
     * bean 注册完成后才能有 bean
     *
     * @param beanName beanName
     * @return bean
     */
    @Override
    public Object getBean(String beanName) {
        return doGetBean(beanName);
    }

    private Object doGetBean(String beanName) {
        Object bean = singletonObjects.get(beanName);
        if (Objects.nonNull(bean)) {
            return bean;
        }
        // 根据 beanDefinition 创建 bean
        AnnotatedGenericBeanDefinition bd = (AnnotatedGenericBeanDefinition) beanDefinitionMap.get(beanName);
        Object createBean = createBean(beanName, bd);
        if ("singleton".equalsIgnoreCase(bd.getScope())) {
            // createBean 完成了 beanDefinition 转真正实体类对象
            singletonObjects.put(beanName, createBean);
        }
        return createBean;
    }

    private Object createBean(String beanName, AnnotatedGenericBeanDefinition bd) {
        try {
            return bd.getClazz().getConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void registerBeanDefinition(String beanName, AnnotatedBeanDefinition beanDefinition) {
        // 源码中此处还有其他判断逻辑
        this.beanDefinitionMap.put(beanName, beanDefinition);
    }

    public void doScan() {
        for (String beanName : beanDefinitionMap.keySet()) {
            AnnotatedGenericBeanDefinition bd = (AnnotatedGenericBeanDefinition) beanDefinitionMap.get(beanName);
            // 找到配置类
            if (bd.getClazz().isAnnotationPresent(ComponentScan.class)) {
                ComponentScan componentScan = (ComponentScan) bd.getClazz().getAnnotation(ComponentScan.class);
                String basePackage = componentScan.value();
                // 加载指定路径下的资源
                URL resource = this.getClass().getClassLoader()
                        .getResource(basePackage.replace(".", "/"));
                // 获取全路径名资源
                File file = new File(resource.getFile());
                if (file.isDirectory()) {
                    for (File f : file.listFiles()) {
                        try {
                            // 加载配置类
                            Class<?> clazz = this.getClass().getClassLoader()
                                    .loadClass(basePackage.concat(".").concat(f.getName().split("\\.")[0]));
                            if (clazz.isAnnotationPresent(Service.class)) {
                                String name = clazz.getAnnotation(Service.class).value();
                                AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition();
                                abd.setClazz(clazz);
                                // Scope 注解解析
                                if (clazz.isAnnotationPresent(Scope.class)) {
                                    abd.setScope(clazz.getAnnotation(Scope.class).value());
                                } else {
                                    abd.setScope("singleton");
                                }
                                beanDefinitionMap.put(name, abd);
                                // 记录 bean 名称
                                beanDefinitionNames.add(name);
                            }
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

        }
    }

    public void preInstantiateSingletons() {
        // 初始化 bean
        // 防止并发环境下遍历出现问题
        List<String> beanNames = new ArrayList<>(beanDefinitionNames);
        for (String beanName : beanNames) {
            AnnotatedGenericBeanDefinition beanDefinition = (AnnotatedGenericBeanDefinition) beanDefinitionMap.get(beanName);
            if ("singleton".equalsIgnoreCase(beanDefinition.getScope())) {
                // getBean 方法里创建单例对象并保存到单例池当中
                getBean(beanName);
            }
        }
    }
}
