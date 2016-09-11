package com.boe.service.rabbitmqTest;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * Created by Boe on 2016-09-10.
 */
public class RpcClient {
    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";
    private String replyQueueName;
    private QueueingConsumer consumer;


    public RpcClient() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("huabo");
        factory.setPassword("123456");
        factory.setPort(AMQP.PROTOCOL.PORT);
        connection = factory.newConnection();
        channel = connection.createChannel();
        //• 注册'回调'队列，这样就可以收到RPC响应
        replyQueueName = channel.queueDeclare().getQueue();
        consumer = new QueueingConsumer(channel);
        channel.basicConsume(replyQueueName,true,consumer);
    }


    public String call(String message) throws IOException, InterruptedException {
        String response = null;
        String corrid = UUID.randomUUID().toString();
        //发送请求消息，消息使用了两个属性：replyTo和correlationId
        AMQP.BasicProperties props = new AMQP.BasicProperties().builder().
                correlationId(corrid).replyTo(replyQueueName).build();
        channel.basicPublish("",requestQueueName,props,message.getBytes());

        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            if(delivery.getProperties().getCorrelationId().equals(corrid)){
                response = new String(delivery.getBody());
                break;
            }
        }

        return response;
    }

    public void close() throws Exception {
        connection.close();
    }


}
