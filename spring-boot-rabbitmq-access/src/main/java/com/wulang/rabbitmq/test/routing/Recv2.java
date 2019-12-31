package com.wulang.rabbitmq.test.routing;

import com.rabbitmq.client.*;
import com.wulang.rabbitmq.test.RabbitMQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wulang
 * @create 2019/12/13 0013/17:17
 */
public class Recv2 {
    private static final String QUEUE_NAME="test_queue_direct_two";
    private final static String EXCHANGER_NAME = "test_exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = RabbitMQConnectionUtils.getConnection();

        //从连接中获取频道
        final Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //绑定队列到交换机 转发器
        String routingKey = "error";
        String routingKey2 = "info";
        String routingKey3 = "warning";
        channel.queueBind(QUEUE_NAME,EXCHANGER_NAME,routingKey);
        channel.queueBind(QUEUE_NAME,EXCHANGER_NAME,routingKey2);
        channel.queueBind(QUEUE_NAME,EXCHANGER_NAME,routingKey3);

        //保证一次只发一个
        channel.basicQos(1);

        DefaultConsumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("[2] Recv msg:" + msg);

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[2] done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        boolean autoAck = false;

        channel.basicConsume(QUEUE_NAME,autoAck,consumer);

        System.out.println("[Consumer 2 start]");


    }
}
