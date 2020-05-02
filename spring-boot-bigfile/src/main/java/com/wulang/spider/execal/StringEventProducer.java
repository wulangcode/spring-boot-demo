package com.wulang.spider.execal;

import com.lmax.disruptor.RingBuffer;

/**
 * 生产者
 *
 * @author wulang
 * @create 2020/3/10/14:23
 */
public class StringEventProducer {
    //核心类，环形数组
    public final RingBuffer<StringEvent> ringBuffer;

    /**
     * 通过构造方法初始化环形数组这个类
     *
     * @param ringBuffer
     */
    public StringEventProducer(RingBuffer<StringEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 生产事件（数据）的方法
     * 往环形数组里面放数据
     *
     * @param data
     */
    public void onData(String data) {
        //1.ringBuffer 事件队列的下一个槽，也就是我们要放置数据的位置
        long sequence = ringBuffer.next();
        try {
            //2.取出空的事件队列
            StringEvent stringEvent = ringBuffer.get(sequence);
            //3.获取事件队列传递的数据
            stringEvent.setValue(data);
        } finally {
            //4.发布事件
            ringBuffer.publish(sequence);
            System.out.println("生产者生产数据——" + data);
        }
    }
}
