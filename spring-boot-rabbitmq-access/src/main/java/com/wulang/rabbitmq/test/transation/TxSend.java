package com.wulang.rabbitmq.test.transation;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wulang.rabbitmq.test.RabbitMQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * TxSelect TxCommit TxRollBack
 *
 * TxSelect : 用于将当前channel设置成transation模式
 * TxCommit : 用于提交事务
 * TxRollBack : 用于回滚事务
 *
 * @author wulang
 * @create 2019/12/13 0013/17:26
 */
public class TxSend {
    private final static String QUEUE_NAME = "test_queue_tx";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = RabbitMQConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "hello tx";

        try {
            channel.txSelect();

            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

            int ex = 1/0;

            channel.txCommit();
        } catch (Exception e) {
            e.printStackTrace();
            channel.txRollback();
            System.out.println("Send msg rollback");
        }

        System.out.println("Send msg"+msg);

        channel.close();
        connection.close();
    }
}
