package com.wulang.rabbitmq.test;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wulang
 * @create 2019/12/13/15:04
 */
public class RabbitMQConnectionUtils {

    /**
     * 若connection timeout 连接不上尝试关闭防火墙或者放开端口访问
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Connection getConnection() throws IOException, TimeoutException {
        //定义一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();

        //设置服务器地址
        connectionFactory.setHost("148.70.34.49");

        //AMQP 15672
        connectionFactory.setPort(5672);

        //vhost
        connectionFactory.setVirtualHost("/");

        //设置用户名
        connectionFactory.setUsername("guest");

        //设置密码
        connectionFactory.setPassword("guest");

        return connectionFactory.newConnection();
    }
}
