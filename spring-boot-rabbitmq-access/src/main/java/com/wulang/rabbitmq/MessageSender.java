package com.wulang.rabbitmq;

import com.wulang.rabbitmq.common.DetailRes;
import com.wulang.rabbitmq.common.MessageWithTime;


public interface MessageSender {
    DetailRes send(Object message);

    DetailRes send(MessageWithTime messageWithTime);
}
