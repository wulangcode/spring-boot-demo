package com.wulang.rabbitmq.test.test2;

import com.rabbitmq.client.*;
import com.wulang.rabbitmq.test.RabbitMQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wulang
 * @create 2019/12/13 0013/16:32
 */
public class Recv {
    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        //获取连接
        Connection connection = RabbitMQConnectionUtils.getConnection();

        //从连接中获取频道
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //定义消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("[1] Recv msg:" + msg);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[1] done");
                }
            }
        };

        //轮询分发 一个消费者拿一个处理 不考虑消费者消费能力 或者消费是否结束 两个消费者各一半
        //自动应答 消费完会自动返回消费情况
        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME,autoAck,defaultConsumer);

    }
}
