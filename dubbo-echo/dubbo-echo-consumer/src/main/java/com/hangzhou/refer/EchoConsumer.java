package com.hangzhou.refer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hangzhou.service.EchoService;
import org.springframework.stereotype.Component;

/**
 * 特殊的包装
 *
 * @Author Faye
 * @Date 2022/12/7 11:17
 */
@Component
public class EchoConsumer {
    @Reference
    private EchoService echoService;

    public String echo(String message) {
        return echoService.echo(message);
    }
}
