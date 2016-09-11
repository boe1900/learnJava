package com.boe.service.rabbitmqTest;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Boe on 2016-09-09.
 */
public class Receive {
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

        //创建对列消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //指定消费对列
        channel.basicConsume(QUEUE_NAME,true,consumer);
        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("Received Message：'" + message + "'");
        }




    }

}
