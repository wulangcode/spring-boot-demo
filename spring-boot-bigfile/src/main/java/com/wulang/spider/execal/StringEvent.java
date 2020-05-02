package com.wulang.spider.execal;

/**
 * 定义事件event：通过disruptor进行交换的数据类型
 *
 * @author wulang
 * @create 2020/3/10/14:11
 */
public class StringEvent {
    //放一个字符串数据
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
