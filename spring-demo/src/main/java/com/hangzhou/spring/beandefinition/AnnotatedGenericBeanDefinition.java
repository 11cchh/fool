package com.hangzhou.spring.beandefinition;

/**
 * @Author Faye
 * @Date 2023/1/7 14:51
 */
public class AnnotatedGenericBeanDefinition implements AnnotatedBeanDefinition {
    /**
     * 类
     */
    private Class clazz;
    /**
     * 类型
     */
    private String scope;

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
