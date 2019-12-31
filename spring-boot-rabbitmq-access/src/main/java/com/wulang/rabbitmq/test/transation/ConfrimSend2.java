package com.wulang.rabbitmq.test.transation;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wulang.rabbitmq.test.RabbitMQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 普通模式：多条批量，发完之后再确认
 * @author wulang
 * @create 2019/12/13 0013/17:41
 */
public class ConfrimSend2 {
    private final static String QUEUE_NAME = "test_queue_confrim_one";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        Connection connection = RabbitMQConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //生产者调用confirmSelect() 将channel设为confirm模式
        channel.confirmSelect();

        String msg = "hello confirm batch";

        //批量发送
        for (int i = 0 ;i < 50 ; i++){
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            Thread.sleep(1000);
        }

        //确认
        if (!channel.waitForConfirms()){
            System.out.println("message send fail!");
        }else{
            System.out.println("Send msg"+msg);
        }

        channel.close();
        connection.close();
    }
}
