package com.wulang.rocketmq.mq.order;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

/**
 * 下面用订单进行分区有序的示例。
 * 一个订单的顺序流程是：创建、付款、推送、完成。
 *
 * 订单号相同的消息会被先后发送到同一个队列中，消费时，同一个OrderId获取到的肯定是同一个队列。
 * @author wulang
 * @create 2019/12/25/15:20
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        //1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2.指定Nameserver地址
        producer.setNamesrvAddr("154.8.216.45:9876;148.70.34.49:9876");
        //3.启动producer
        producer.start();
        //构建消息集合
        List<OrderStep> orderSteps = OrderStep.buildOrders();
        //发送消息
        for (int i = 0; i < orderSteps.size(); i++) {
            String body = orderSteps.get(i) + "";
            Message message = new Message("OrderTopic",
                    "Order",
                    "i" + i, body.getBytes());
            /**
             * 参数一：消息对象
             * 参数二：消息队列的选择器
             * 参数三：选择队列的业务标识（订单ID）
             */
            SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                /**
                 * @param mqs：队列集合
                 * @param msg：消息对象
                 * @param arg：业务标识的参数
                 * @return
                 */
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    //根据订单id选择发送queue
                    long orderId = (long) arg;
                    long index = orderId % mqs.size();
                    return mqs.get((int) index);
                }
            }, orderSteps.get(i).getOrderId());

            System.out.println("发送结果：" + sendResult);
        }
        producer.shutdown();
    }
}
