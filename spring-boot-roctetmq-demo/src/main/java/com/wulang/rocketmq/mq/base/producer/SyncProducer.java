package com.wulang.rocketmq.mq.base.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送同步消息
 *
 *这种可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知。
 * @author wulang
 * @create 2019/12/25/14:37
 */
public class SyncProducer {
    public static void main(String[] args) throws Exception {
        //1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2.指定Nameserver地址
        producer.setNamesrvAddr("154.8.216.45:9876;148.70.34.49:9876");
        //3.启动producer
        producer.start();

        for (int i = 0; i < 10; i++) {
            //4.创建消息对象，指定主题Topic、Tag和消息体
            /**
             * 参数一：消息主题Topic
             * 参数二：消息Tag
             * 参数三：消息内容
             */
            Message msg = new Message("springboot-mq", "Tag1", ("Hello World" + i).getBytes());
            //5.发送消息
            SendResult result = producer.send(msg);
            //发送状态
            SendStatus status = result.getSendStatus();
            //消息ID
            String msgId = result.getMsgId();
            //接收队列ID
            int queueId = result.getMessageQueue().getQueueId();
            System.out.println("发送结果:" + result);
            System.out.println("发送状态:" + status + "，消息ID：" + msgId + "，接收队列ID" + queueId);

            //线程睡1秒
            TimeUnit.SECONDS.sleep(1);
        }

        //6.关闭生产者producer
        producer.shutdown();
    }
}
