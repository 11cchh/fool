package com.hangzhou;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Author Faye
 * @Date 2022/12/7 10:04
 */
public class XmlProvider {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"spring/echo-provider.xml"});
        applicationContext.start();

        System.in.read();
    }
}
