package com.wulang.rabbitmq.test.test2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wulang.rabbitmq.test.RabbitMQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *  轮询分发
 *
 * @author wulang
 * @create 2019/12/13 0013/16:31
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

        for (int i = 0 ; i < 50 ; i++){
            String msg = "hello "+i;
            System.out.println("[WQ] send:"+msg);
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            Thread.sleep(i*20);
        }

        channel.close();
        connection.close();

    }
}
