package com.wulang.rabbitmq.test.test4;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wulang.rabbitmq.test.RabbitMQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 订阅模式模型
 *
 * 1、一个生产者 多个消费者
 * 2、每个消费者都有自己的队列
 * 3、生产者没有直接把消息发送给队列，而是先发送给交换机exchange
 * 4、每个队列都要绑定到交换机上
 * 5、生产者发送的消息是经过交换机的，然后到达队列，就能实现一个消息被多个消费者消费
 *
 * 应用场景: 比如注册之后需要发送邮件 同时需要发送短信
 *
 * @author wulang
 * @create 2019/12/13 0013/17:08
 */
public class PublishSend {
    private final static String EXCHANGER_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = RabbitMQConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        // 分发
        channel.exchangeDeclare(EXCHANGER_NAME,"fanout");

        String msg = "hello exchange";
        //消息丢失了，因为交换机没有存储的能力，rabbitMQ中只有队列有存储能力，此时还没有队列绑定到该交换机上，所以数据丢失了。
        channel.basicPublish(EXCHANGER_NAME,"",null,msg.getBytes());

        System.out.println("Send msg"+msg);

        channel.close();
        connection.close();
    }
}
