package com.hangzhou.aop;

import com.hangzhou.pojo.OperationLogVO;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数转化
 *
 * @Author Faye
 * @Date 2022/11/29 17:26
 */
public interface Convert<Object> {
    /**
     * 参数转化
     *
     * @param object 参数
     * @return 指定操作日志参数
     */
    OperationLogVO convert(Object object);
}
