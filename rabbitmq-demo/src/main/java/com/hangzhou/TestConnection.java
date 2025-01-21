package com.hangzhou;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Author Faye
 * @Date 2023/3/6 09:52
 */
public class TestConnection {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        Map<String, Object> exchangeArguments = new HashMap<>();
        exchangeArguments.put("alternate-exchange", "aeExchange");
        Map<String, Object> queueArguments = new HashMap<>();
        queueArguments.put("x-message-ttl", 10000);
        queueArguments.put("x-dead-letter-exchange", "deadExchange");
        queueArguments.put("x-max-length", "1");

        // 创建 exchange、queue 并绑定
        channel.exchangeDeclare("myExchange", "direct", true, false, exchangeArguments);
        channel.queueDeclare("myQueue", true, false, false , queueArguments);
        channel.queueBind("myQueue", "myExchange", "myRoutingKey");

        // 创建备份 exchange
        channel.exchangeDeclare("aeExchange", "fanout", true);
        channel.queueDeclare("aeQueue", true, false, false , null);
        channel.queueBind("aeQueue", "aeExchange", "aeKey");

        // 创建死信 exchange、exchange
        channel.exchangeDeclare("deadExchange", "fanout", true);
        channel.queueDeclare("deadQueue", true, false, false , null);
        channel.queueBind("deadQueue", "deadExchange", "aeKey");


        // 发送消息
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
                // 消息持久化设置
                .deliveryMode(2)
                .contentType("text/plain")
                .build();
        byte[] msg = "Hello World!".getBytes();
        channel.basicPublish("myExchange", "myRoutingKey", false, basicProperties, msg);

        // 消费消息
        // callback 参数设置为 true 时需要手动的 ack，保证消息被正确接受且处理
        channel.basicConsume("myQueue", false, "myConsumer", new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                long deliveryTag = envelope.getDeliveryTag();
                System.out.println("接收到消息：" + new String(body) + "，deliveryTag：" + deliveryTag);
//                channel.basicAck(deliveryTag, false);
                // 拒绝消息并且 requeue 参数为 false
                // requeue 参数如果为 true 消息会重新放回队列中
                channel.basicReject(deliveryTag, false);
            }
        });
    }
}
