package com.wulang.rabbitmq.test.test3;

import com.rabbitmq.client.*;
import com.wulang.rabbitmq.test.RabbitMQConnectionUtils;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * @author wulang
 * @create 2019/12/13 0013/16:38
 */
public class Recv {
    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        //获取连接
        Connection connection = RabbitMQConnectionUtils.getConnection();

        //从连接中获取频道
        final Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //保证一次只分发一个
        channel.basicQos(1);

        //定义消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("[1] Recv msg:" + msg + new Date());

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[1] done" + new Date());
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };

        //公平分发 消费能力快的拿得快 能者多劳
        //使用公平分发 必须关闭自动应答 ack改成手动
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck,defaultConsumer);

    }
}
