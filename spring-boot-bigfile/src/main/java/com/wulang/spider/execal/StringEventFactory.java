package com.wulang.spider.execal;

import com.lmax.disruptor.EventFactory;

/**
 * 定义事件工厂
 *
 * @author wulang
 * @create 2020/3/10/14:13
 */
public class StringEventFactory implements EventFactory<StringEvent> {
    @Override
    public StringEvent newInstance() {
        return new StringEvent();
    }
}
