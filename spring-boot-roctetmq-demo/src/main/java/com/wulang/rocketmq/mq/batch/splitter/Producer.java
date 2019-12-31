package com.wulang.rocketmq.mq.batch.splitter;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 如果消息的总长度可能大于4MB时，这时候最好把消息进行分割
 *
 * @author wulang
 * @create 2019/12/25/16:18
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        //1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2.指定Nameserver地址
        producer.setNamesrvAddr("154.8.216.45:9876;148.70.34.49:9876");
        //3.启动producer
        producer.start();


        List<Message> msgs = new ArrayList<Message>();


        //4.创建消息对象，指定主题Topic、Tag和消息体
        /**
         * 参数一：消息主题Topic
         * 参数二：消息Tag
         * 参数三：消息内容
         */
        Message msg1 = new Message("BatchTopic", "Tag1", ("Hello World" + 1).getBytes());
        Message msg2 = new Message("BatchTopic", "Tag1", ("Hello World" + 2).getBytes());
        Message msg3 = new Message("BatchTopic", "Tag1", ("Hello World" + 3).getBytes());

        msgs.add(msg1);
        msgs.add(msg2);
        msgs.add(msg3);
        //把大的消息分裂成若干个小的消息
        ListSplitter splitter = new ListSplitter(msgs);
        //5.发送消息
//        SendResult result = producer.send(msgs);
        while (splitter.hasNext()) {
            try {
                List<Message>  listItem = splitter.next();
                SendResult result = producer.send(listItem);
                //发送状态
                SendStatus status = result.getSendStatus();
                System.out.println("发送结果:" + result);
            } catch (Exception e) {
                e.printStackTrace();
                //处理error
            }

        //线程睡1秒
        TimeUnit.SECONDS.sleep(1);


        //6.关闭生产者producer
        producer.shutdown();
    }
}
}
