package com.wulang.spider.execal;

import com.lmax.disruptor.EventHandler;

/**
 * 消费者 -- 定义事件的具体消费的实现
 *
 * @author wulang
 * @create 2020/3/10/14:16
 */
public class StringEventHandler implements EventHandler<StringEvent> {
    @Override
    public void onEvent(StringEvent event, long sequence, boolean endOfBatch) throws Exception {
        //测试只是做一个打印，没有处理业务逻辑
        System.out.println("消费者：" + event.getValue() + "-->sequence=" + sequence + ",endOfBatch=" + endOfBatch);
    }
}
