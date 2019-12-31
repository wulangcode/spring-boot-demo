package com.wulang.redis.common;

/**
 * @author wulang
 * @create 2019/11/26/16:10
 */
public class Holder<T> {
    private T value;
    public Holder() {
    }
    public Holder(T value) {
        this.value = value;
    }
    public void value(T value) {
        this.value = value;
    }
    public T value() {
        return value;
    }
}
