package com.wulang.rabbitmq.test.test3;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wulang.rabbitmq.test.RabbitMQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * 默认情况下，消息队列不管消费者是否处理完毕消息，都会继续发送下一条消息，有一种机制可以避免这种情况，这种机制使得，使
 * 得每个消费者发送确认信号前，消息队列不会发送消息，也就是一个消费者发送确认前一次只处理一条消息，同时消息会在消息队列堆积
 *
 * @author wulang
 * @create 2019/12/13 0013/16:37
 */
public class Send {
    private static final String  QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        //获取连接
        Connection connection = RabbitMQConnectionUtils.getConnection();

        //从连接中取得一个频道
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        /**
         * 每个消费者 发送确认消息之前,消息队列不发送下一个消息到消费者,一次只处理一个消息
         *
         * 限制发送给同一个消费者 不得超过一条消息
         */

        int perfetchCount = 1;
        channel.basicQos(perfetchCount);
        for (int i = 0 ; i < 50 ; i++){
            String msg = "hello "+i;
            System.out.println("[WQ] send:"+msg);
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            Thread.sleep(i*5);
        }

        channel.close();
        connection.close();

    }
}
