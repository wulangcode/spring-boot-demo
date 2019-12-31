package com.wulang.producer.producer;

import com.wulang.entity.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Order order){
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(order.getMessageId());
        rabbitTemplate.convertAndSend("order-exchange",
                "order.abcd",
                order,
                correlationData);
        /**
         * 还要在 rabbitmq 控制台配置exchange和queue，并绑定
         * 加绑定在控制台的exchange和queues哪一块都可以
         */
    }
}
