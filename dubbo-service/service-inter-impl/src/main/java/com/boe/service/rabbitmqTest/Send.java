package com.boe.service.rabbitmqTest;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Boe on 2016-09-09.
 */
public class Send {
    private static final String QUEUE_NAME= "helloworld";

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
        //指定一个对列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //发送消息
        String message = "我擦";
        //往对列中发一条消息
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("Send Message:'"+message+"'");
        //关闭频道和连接
        channel.close();
        connection.close();

    }


}
