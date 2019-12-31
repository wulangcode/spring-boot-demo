package com.wulang.producer.config.rabbitmq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

/**
 * 为每条消息设置过期时间
 *
 * 可根据实际情况加入自定义的参数
 * @author wulang
 * @create 2019/12/17 0017/17:49
 */
public class MyMessagePostProcessor implements MessagePostProcessor {
    /**
     * props : 消息的基本属性集，其包含14 个属性成员，分别有
     * contentType 、
     * contentEncoding 、headers ( Map<String ， Object>) 、deliveryMode 、priority 、
     * correlationId 、replyTo 、expiration 、messageId、timestamp 、type 、userId 、
     * appId、clusterId。
     */
    private final Integer ttl;


    public MyMessagePostProcessor(final Integer ttl) {
        this.ttl = ttl;
    }

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        message.getMessageProperties().getHeaders().put("expiration", ttl.toString());
        return message;
    }
}
