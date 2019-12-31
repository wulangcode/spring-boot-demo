package com.wulang.rabbitmq.example;

import com.wulang.rabbitmq.MessageProcess;
import com.wulang.rabbitmq.common.DetailRes;

/**
 * (4) MessageProcess(用户自定义处理接口)示例，本例中我们只是简单的将信息打印出来
 */
public class UserMessageProcess implements MessageProcess<UserMessage> {
    @Override
    public DetailRes process(UserMessage userMessage) {
        System.out.println(userMessage);

        return new DetailRes(true, "");
    }
}
