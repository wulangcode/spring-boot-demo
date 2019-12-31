package com.wulang.rabbitmq.test.test1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wulang.rabbitmq.test.RabbitMQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *  简单版本的消息发送与接收
 *
 * @author wulang
 * @create 2019/12/13/15:08
 */
public class Send {
    private static final String QUEUE_NAME="test";

    public static void main(String[] args) throws IOException, TimeoutException {

        //获取一个连接
        Connection connection = RabbitMQConnectionUtils.getConnection();

        //从连接中获取一个通道
        Channel channel = connection.createChannel();

        //创建队列申明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "测试数据";

        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        System.out.println("send msg:"+msg);

        channel.close();

        connection.close();

    }
}
