package com.hangzhou;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.hangzhou.refer.EchoConsumer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Faye
 * @Date 2022/12/7 11:19
 */
public class AnnotationConsumer {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
        EchoConsumer echoConsumer = context.getBean(EchoConsumer.class);
        String message = echoConsumer.echo("hello world!");
        System.out.println("result: " + message);
    }

    @Configuration
    @EnableDubbo(scanBasePackages = {"com.hangzhou"})
    @ComponentScan(value = {"com.hangzhou.refer"})
    static class ConsumerConfiguration {
        @Bean
        public ApplicationConfig applicationConfig() {
            ApplicationConfig config = new ApplicationConfig();
            config.setName("echo-annotation-consumer");
            return config;
        }

        @Bean
        public ConsumerConfig consumerConfig() {
            return new ConsumerConfig();
        }

        /**
         * 使用 Zookeeper 作为注册中心
         *
         * @return
         */
        @Bean
        public RegistryConfig registryConfig() {
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setProtocol("zookeeper");
            registryConfig.setAddress("localhost");
            registryConfig.setPort(2181);
            return registryConfig;
        }
    }
}
