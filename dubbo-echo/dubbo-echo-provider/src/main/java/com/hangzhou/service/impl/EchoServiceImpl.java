package com.hangzhou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.hangzhou.service.EchoService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Service 会让实现类被 Spring 容器所管理
 *
 * @Author Faye
 * @Date 2022/12/7 09:56
 */
@Service
public class EchoServiceImpl implements EchoService {
    @Override
    public String echo(String message) {
        String now = new SimpleDateFormat("HH:mm:ss").format(new Date());
        System.out.println("[" + now + "] Hello " + message
                + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return message;
    }
}
