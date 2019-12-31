package com.wulang.rabbitmq.test.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wulang.rabbitmq.test.RabbitMQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Exchange
 *
 * 交换机，转发器，一方面接收生产者的消息，另一方面向队列推送消息
 * @author wulang
 * @create 2019/12/13 0013/17:15
 */
public class Send {
    private final static String EXCHANGER_NAME = "test_exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = RabbitMQConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        // 声明交换机
        channel.exchangeDeclare(EXCHANGER_NAME,"direct");

        String msg = "hello exchange direct";

        //发送error两个队列都会收到消息
        String routingKey = "error";
        //发送info 只有消费者2会收到消息
        String routingKey2 = "info";
        channel.basicPublish(EXCHANGER_NAME,routingKey2,null,msg.getBytes());

        System.out.println("Send msg"+msg);

        channel.close();
        connection.close();
    }
}
