package com.wulang.rabbitmq;

import com.wulang.rabbitmq.common.DetailRes;


public interface MessageProcess<T> {
    DetailRes process(T message);
}
