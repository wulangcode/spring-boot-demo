package com.wulang.producer.producer;

import com.wulang.entity.Order;
import com.wulang.producer.config.rabbitmq.MyMessagePostProcessor;
import com.wulang.producer.constant.Constants;
import com.wulang.producer.mapper.BrokerMessageLogMapper;
import com.wulang.producer.utils.FastJsonConvertUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * confirmCallback 确认模式
 * returnCallback 未投递到 queue 退回模式
 * shovel-plugin 跨机房可靠投递
 *
 * 详细说明请参考文档：https://www.cnblogs.com/wangiqngpei557/p/9381478.html
 */
@Component
public class RabbitOrderSender {
    //自动注入RabbitTemplate模板类
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    /**
     * 回调函数: confirm确认
     */
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.err.println("correlationData: " + correlationData);
            String messageId = correlationData.getId();
            if(ack){
                //如果confirm返回成功 则进行更新
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageId, Constants.ORDER_SEND_SUCCESS, new Date());
            } else {
                //失败则进行具体的后续操作:重试 或者补偿等手段
                /**
                 * 在此需根据自己实际业务去捕获失败类型
                 * 需要提前了解，是队列满了，还是配置镜像模式中，消息没有被复制到另外的队列而发生的异常
                 */
                System.err.println("异常处理...");
            }
        }
    };

    /**
     * confrim 模式只能保证消息到达 broker，不能保证消息准确投递到目标 queue 里。
     * 在有些业务场景下，我们需要保证消息一定要投递到目标 queue 里，此时就需要用到 return 退回模式。
     */
    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
            byte[] body = message.getBody();
            String string = body.toString();
            Order order = FastJsonConvertUtil.convertJSONToObject(string, Order.class);
            //TODO 取出数据后根据实际业务做调整
        }
    };

    /**
     * 发送消息方法调用: 构建自定义对象消息
     *
     * @param order
     * @throws Exception
     */
    public void sendOrder(Order order) throws Exception {
        /**
         * 通过实现 ConfirmCallback 接口，消息发送到 Broker 后触发回调，确认消息是否到达 Broker 服务器，
         * 也就是只确认是否正确到达 Exchange 中，
         */
        rabbitTemplate.setConfirmCallback(confirmCallback);
        /**
         *  这样如果未能投递到目标 queue 里将调用 returnCallback ，
         *  可以记录下详细到投递数据，定期的巡检或者自动纠错都需要这些数据。
         */
        rabbitTemplate.setReturnCallback(returnCallback);
        //消息唯一ID
        CorrelationData correlationData = new CorrelationData(order.getMessageId());
        /**
         * 为消息设置过期时间
         */
        MyMessagePostProcessor myMessagePostProcessor = new MyMessagePostProcessor(1000);
        /**
         * convertAndSend(String exchange,
         *               String routingKey,
         *               final Object message,
         *               final MessagePostProcessor messagePostProcessor,
         *               CorrelationData correlationData)
         */
        rabbitTemplate.convertAndSend(
                "order-exchange",
                "order.ABC",
                order,
                myMessagePostProcessor,
                correlationData);
    }
}
