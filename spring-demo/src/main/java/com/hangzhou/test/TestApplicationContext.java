package com.hangzhou.test;

import com.hangzhou.spring.applicationcontext.AnnotationConfigApplicationContext;
import com.hangzhou.test.bean.RoleService;
import com.hangzhou.test.bean.UserService;
import com.hangzhou.test.config.AppConfig;

/**
 * @Author Faye
 * @Date 2023/1/7 10:29
 */
public class TestApplicationContext {
    public static void main(String[] args) {
        // 创建 applicationContext
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfig.class);
        UserService singleton = (UserService) applicationContext.getBean("userService");
        UserService singleton2 = (UserService) applicationContext.getBean("userService");
        System.out.println(singleton);
        System.out.println(singleton2);

        RoleService roleService = (RoleService) applicationContext.getBean("roleService");
        RoleService roleService2 = (RoleService) applicationContext.getBean("roleService");
        System.out.println(roleService);
        System.out.println(roleService2);
    }
}
