package com.wulang.rabbitmq.test.transation;

import com.rabbitmq.client.*;
import com.wulang.rabbitmq.test.RabbitMQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wulang
 * @create 2019/12/13 0013/17:40
 */
public class ConfirmRecv1 {
    private final static String QUEUE_NAME = "test_queue_confrim_one";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = RabbitMQConnectionUtils.getConnection();

        //从连接中获取频道
        final Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        boolean autoAck = true;

        channel.basicConsume(QUEUE_NAME,autoAck,new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("[1] Recv tx msg:" + msg);
            }
        });

        System.out.println("[Consumer 1 start]");


    }
}
