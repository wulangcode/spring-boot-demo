package com.wulang.rabbitmq.test.test1;

import com.rabbitmq.client.*;
import com.wulang.rabbitmq.test.RabbitMQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wulang
 * @create 2019/12/13 0013/16:27
 */
public class Recv {

        private static final String QUEUE_NAME = "test";

        public static void main(String[] args) throws IOException, TimeoutException {

            //获取连接
            Connection connection = RabbitMQConnectionUtils.getConnection();

            //从连接中获取通道
            Channel channel = connection.createChannel();

            //创建队列申明
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);

            //定义消费者
            DefaultConsumer consumer = new DefaultConsumer(channel) {

                /**
                 * 获取到达消息
                 * @param consumerTag
                 * @param envelope
                 * @param properties
                 * @param body
                 * @throws IOException
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body, "utf-8");
                    System.out.println("recv msg:" + msg);
                }
            };

            //监听队列
            channel.basicConsume(QUEUE_NAME,true,consumer);

        }
}
