package com.wulang.rabbitmq.example;

import com.wulang.rabbitmq.MQAccessBuilder;
import com.wulang.rabbitmq.MessageProcess;
import com.wulang.rabbitmq.ThreadPoolConsumer;
import com.wulang.rabbitmq.common.Constants;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * (6) 线程池consumer示例 在main函数中，我们使用一个独立线程发送数据，并使用线程池接收数据。
 */
@Service
public class PoolExample {
    private static final String EXCHANGE = "example";
    private static final String ROUTING = "user-example";
    private static final String QUEUE = "user-example";

    @Autowired
    ConnectionFactory connectionFactory;

    private ThreadPoolConsumer<UserMessage> threadPoolConsumer;

    @PostConstruct
    public void init() {
        MQAccessBuilder mqAccessBuilder = new MQAccessBuilder(connectionFactory);
        MessageProcess<UserMessage> messageProcess = new UserMessageProcess();

        threadPoolConsumer = new ThreadPoolConsumer.ThreadPoolConsumerBuilder<UserMessage>()
                .setThreadCount(Constants.THREAD_COUNT).setIntervalMils(Constants.INTERVAL_MILS)
                .setExchange(EXCHANGE).setRoutingKey(ROUTING).setQueue(QUEUE)
                .setMQAccessBuilder(mqAccessBuilder).setMessageProcess(messageProcess)
                .build();
    }

    public void start() throws IOException {
        threadPoolConsumer.start();
    }

    public void stop() {
        threadPoolConsumer.stop();
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        PoolExample poolExample = ac.getBean(PoolExample.class);
        final SenderExample senderExample = ac.getBean(SenderExample.class);

        poolExample.start();

        new Thread(new Runnable() {
            int id = 0;

            @Override
            public void run() {
                while (true) {
                    senderExample.send(new UserMessage(id++, "" + System.nanoTime()));

                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
