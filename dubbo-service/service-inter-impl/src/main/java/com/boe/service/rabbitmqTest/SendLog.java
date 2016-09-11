package com.boe.service.rabbitmqTest;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * Created by Boe on 2016-09-10.
 */
public class SendLog {
    private static final String EXCHANGE_NAME = "ex_log";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        //连接信息
        factory.setHost("127.0.0.1");
        factory.setUsername("huabo");
        factory.setPassword("123456");
        factory.setPort(AMQP.PROTOCOL.PORT);

        //创建一个连接
        Connection connection = factory.newConnection();
        //创建一个频道
        Channel channel = connection.createChannel();

        /**
         * 声明转发器和类型 可用的转发器类型Direct Topic Headers Fanout 参考：http://melin.iteye.com/blog/691265
         * Direct Exchange – 处理路由键。需要将一个队列绑定到交换机上，要求该消息与一个特定的路由键完全匹配。
         * Fanout Exchange – 不处理路由键。你只需要简单的将队列绑定到交换机上。一个发送到交换机的消息都会被转发到与该交换机绑定的所有队列上。
         * 很像子网广播，每台子网内的主机都获得了一份复制的消息。Fanout交换机转发消息是最快的。
         * Topic Exchange – 将路由键和某模式进行匹配。此时队列需要绑定要一个模式上。符号“#”匹配一个或多个词，符号“*”匹配不多不少一个词。
         * 因此“audit.#”能够匹配到“audit.irs.corporate”，但是“audit.*” 只会匹配到“audit.irs”。
         */
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        String message = new Date().toLocaleString()+" : log something";
        //指定消息发送到的转发器
        channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());

        System.out.println("[x] Sent '" + message + "'");
        channel.close();
        connection.close();


    }


}
