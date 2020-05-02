package com.wulang.spider.execal;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author wulang
 * @create 2020/3/10/14:32
 */
public class Test {
    public static void main(String[] args) {
        //1.创建一个线程工厂提供线程来触发Comsumer的事件处理
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //2.创建工厂
        EventFactory<StringEvent> eventEventFactory = new StringEventFactory();
        //3.创建ringBuffer大小，一定要是2的N次方
        int ringBufferSize = 16;
        //4.创建Disruptor
        Disruptor<StringEvent> disruptor = new Disruptor<StringEvent>(eventEventFactory,//事件（数据）工厂
            ringBufferSize,//环形数组大小
            threadFactory,//线程工厂
            ProducerType.MULTI,//生产者
            new YieldingWaitStrategy());//等待策略
        //5.连接消费端方法
        disruptor.handleEventsWith(new StringEventHandler());

        //6.启动
        disruptor.start();
        //------------------------------------------------以上消费者启动了,以下是生产数据
        //7.创建RingBuffer容器
        RingBuffer<StringEvent> ringBuffer = disruptor.getRingBuffer();
        //8.创建生产者
        StringEventProducer producer = new StringEventProducer(ringBuffer);
        for (int i = 0; i < 10000; i++) {
            producer.onData(String.valueOf(i));
        }
        //9.关闭disruptor和executor
        disruptor.shutdown();
    }
}
