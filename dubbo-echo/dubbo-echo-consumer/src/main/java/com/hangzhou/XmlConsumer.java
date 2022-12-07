package com.hangzhou;

import com.hangzhou.service.EchoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author Faye
 * @Date 2022/12/7 10:17
 */
public class XmlConsumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/echo-consumer.xml"});
        EchoService echoService = (EchoService) context.getBean("echoService");
        String message = echoService.echo("hello world!");
        System.out.println("echo result: " + message);
    }
}
