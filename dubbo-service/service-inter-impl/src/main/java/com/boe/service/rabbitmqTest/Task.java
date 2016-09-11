package com.boe.service.rabbitmqTest;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Boe on 2016-09-10.
 */
public class Task {
    private static final String QUEUE_NAME = "workqueue-durable";


    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("huabo");
        factory.setPassword("123456");
        factory.setPort(AMQP.PROTOCOL.PORT);


        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        boolean durable = true;//设置消息持久化 RabbitMQ不允许使用不同的参数重新定义一个队列，所以已经存在的队列，我们无法修改其属性。
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
        //发送十条消息
        for(int i = 5;i>0;i--){
            String dots = "";
            for(int j=0;j<=i;j++){
                dots += ".";
            }
            String message = "helloworld"+dots;
            //MessageProperties.PERSISTENT_TEXT_PLAIN 标识我们的信息为持久化的
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println("Sent Message：'" + message + "'");
        }

        channel.close();
        connection.close();

    }


}
