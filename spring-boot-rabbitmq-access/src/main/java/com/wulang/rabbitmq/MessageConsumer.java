package com.wulang.rabbitmq;

import com.wulang.rabbitmq.common.DetailRes;


public interface MessageConsumer {
    DetailRes consume();
}
