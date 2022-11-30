package com.hangzhou.aop;

import com.alibaba.fastjson.JSON;
import com.hangzhou.pojo.OperationLogVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 操作日志注解
 *
 * @Author Faye
 * @Date 2022/11/29 17:05
 */
@Slf4j
@Aspect
@Component
public class OperationLogAspect {
    /**
     * 线程池异步执行操作日志的记录
     */
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            1,
            1,
            1,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(100) ,
            new ThreadPoolExecutor.AbortPolicy()
    );

    /**
     * 定义切点
     */
    @Pointcut("@annotation(com.hangzhou.aop.OperationLog)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 方法调用的异常直接抛出
        Object result = proceedingJoinPoint.proceed();
        threadPoolExecutor.execute(() -> {
            try {
                MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
                OperationLog annotation = methodSignature.getMethod().getAnnotation(OperationLog.class);

                Class<? extends Convert> convert = annotation.convert();
                Convert logConvert = convert.newInstance();
                OperationLogVO operationLogVO = logConvert.convert(proceedingJoinPoint.getArgs()[0]);

                operationLogVO.setDesc(annotation.desc());
                operationLogVO.setResult(result.toString());

                log.info("insert operationLog " + JSON.toJSONString(operationLogVO));
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return result;
    }
}
