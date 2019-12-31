package com.wulang.rabbitmq.test.transation;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.wulang.rabbitmq.test.RabbitMQConnectionUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.concurrent.TimeoutException;
import java.util.TreeSet;

/**
 * 异步Confrim模式
 *
 *  Channel对象提供的ConfrimListener()回调方法只包含deliveryTag(当前Channel发送的消息序号)，
 *  我们需要子集为每一个Channel维护一个unconfirm的消息序列号集合，每publish一条数据，集合中的元素加1，每回调一次handleAck方法，
 *  unconfirm集合删掉相应的一条(multiple=false)或多条(multiple=true)记录。
 *
 *  从程序运行效率上看，这个unconfirm集合最好采用有序集合SortedSet存储结构
 *
 * @author wulang
 * @create 2019/12/13 0013/17:44
 */
public class ConfrimSend3 {
    private final static String QUEUE_NAME = "test_queue_confrim_one";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        Connection connection = RabbitMQConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //生产者调用confirmSelect() 将channel设为confirm模式
        channel.confirmSelect();

        //存放未确认的消息
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        //通道添加监听
        channel.addConfirmListener(new ConfirmListener() {
            //没有问题的handleAck
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                if(multiple){
                    System.out.println("handleAck multiple");
                    confirmSet.headSet(deliveryTag+1).clear();
                }else{
                    System.out.println("handleAck multiple false");
                    confirmSet.remove(deliveryTag);
                }
            }

            //handleNack 3s 10s xxx.. 重试
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if(multiple){
                    System.out.println("handleNack multiple");
                    confirmSet.headSet(deliveryTag+1).clear();
                }else{
                    System.out.println("handleNack multiple false");
                    confirmSet.remove(deliveryTag);
                }
            }
        });

        String msg = "hello confirm async";

        while (true){
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            confirmSet.add(seqNo);
        }

    }
}
