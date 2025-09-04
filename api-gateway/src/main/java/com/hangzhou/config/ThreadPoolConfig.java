package com.hangzhou.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * 线程池配置
 *
 * @Author Faye
 * @Date 2025/3/17 15:43
 */
@Configuration
public class ThreadPoolConfig {
    @Bean
    public ThreadPoolExecutor commonThreadPoolExecutor() {
        BlockingQueue<Runnable> queue = new LinkedBlockingDeque<>(1000);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 8, 60, TimeUnit.SECONDS,
                queue, Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolExecutor.prestartCoreThread();
        return threadPoolExecutor;
    }
}
