package com.wulang.rabbitmq.example.conf;

import org.springframework.amqp.rabbit.connection.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * (2) rabbitmq配置 配置我们使用@Configuration实现
 *
 * @author wulang
 * @create 2019/12/16 0016/14:27
 */
@Configuration
public class RabbitMQConf {
    @Bean
    ConnectionFactory connectionFactory() {
        Properties properties = new Properties();

        try {
            Resource res = new ClassPathResource("rabbitmq.properties");
            properties.load(res.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load rabbitmq.properties!");
        }

        String ip = properties.getProperty("ip");
        int port = Integer.parseInt(properties.getProperty("port"));
        String userName = properties.getProperty("user_name");
        String password = properties.getProperty("password");

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(ip, port);

        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setPublisherConfirms(true); // enable confirm mode
        //connectionFactory.getRabbitConnectionFactory().setAutomaticRecoveryEnabled(true);

        return connectionFactory;
    }
}
