package com.boe.service.rabbitmqTest;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Boe on 2016-09-10.
 */
public class Work {
    private static final String QUEUE_NAME = "workqueue-durable";

    public static void main(String[] args) throws Exception {
        int hashCode = Work.class.hashCode();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("huabo");
        factory.setPassword("123456");
        factory.setPort(AMQP.PROTOCOL.PORT);


        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        boolean durable = true;//设置消息持久化 RabbitMQ不允许使用不同的参数重新定义一个队列，所以已经存在的队列，我们无法修改其属性。
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);

        /**
         * ack= true: Round-robin 转发   消费者被杀死，消息会丢失
         * ack=false:消息应答 ，为了保证消息永远不会丢失，RabbitMQ支持消息应答（message acknowledgments）。
         * 消费者发送应答给RabbitMQ，告诉它信息已经被接收和处理，然后RabbitMQ可以自由的进行信息删除。
         * 如果消费者被杀死而没有发送应答，RabbitMQ会认为该信息没有被完全的处理，然后将会重新转发给别的消费者。
         * 通过这种方式，你可以确认信息不会被丢失，即使消者偶尔被杀死。
         * 消费者需要耗费特别特别长的时间是允许的。
         *
         */
        //创建消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);

        boolean ask = false;

        channel.basicConsume(QUEUE_NAME, ask, consumer);

        //公平转发  设置最大服务转发消息数量    只有在消费者空闲的时候会发送下一条信息。
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(hashCode + " Received Message：'" + message + "'");
            doWork(message);
            System.out.println(hashCode + " Received Done");

            //发送应答
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }



    }

    /**
     * 每个点耗时1s
     * @param task
     * @throws InterruptedException
     */
    private static void doWork(String task) throws InterruptedException {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }

}
