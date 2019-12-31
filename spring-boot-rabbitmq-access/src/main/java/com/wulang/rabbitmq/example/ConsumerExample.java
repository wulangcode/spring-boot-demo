package com.wulang.rabbitmq.example;

import com.wulang.rabbitmq.MQAccessBuilder;
import com.wulang.rabbitmq.MessageConsumer;
import com.wulang.rabbitmq.common.DetailRes;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * (5) consumer示例
 */
@Service
public class ConsumerExample {
    private static final String EXCHANGE = "example";
    private static final String ROUTING = "user-example";
    private static final String QUEUE = "user-example";

    @Autowired
    ConnectionFactory connectionFactory;

    private MessageConsumer messageConsumer;

    @PostConstruct
    public void init() throws IOException, TimeoutException {
        MQAccessBuilder mqAccessBuilder = new MQAccessBuilder(connectionFactory);
        messageConsumer = mqAccessBuilder.buildMessageConsumer(EXCHANGE, ROUTING, QUEUE, new UserMessageProcess(), "direct");
    }

    public DetailRes consume() {
        return messageConsumer.consume();
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        ConsumerExample consumerExample = ac.getBean(ConsumerExample.class);

        consumerExample.consume();
    }
}
