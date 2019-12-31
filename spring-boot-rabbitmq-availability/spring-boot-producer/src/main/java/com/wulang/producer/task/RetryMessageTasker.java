package com.wulang.producer.task;

import com.wulang.entity.BrokerMessageLog;
import com.wulang.entity.Order;
import com.wulang.producer.constant.Constants;
import com.wulang.producer.mapper.BrokerMessageLogMapper;
import com.wulang.producer.producer.RabbitOrderSender;
import com.wulang.producer.utils.FastJsonConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class RetryMessageTasker {


    @Autowired
    private RabbitOrderSender rabbitOrderSender;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    /**
     * initialDelay：在服务启动多长时间后运行
     * fixedDelay：固定启动间隔
     */
    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
    public void reSend(){
        System.out.println("-----------定时任务开始-----------");
        //pull status = 0 and timeout message
        List<BrokerMessageLog> list = brokerMessageLogMapper.query4StatusAndTimeoutMessage();
        list.forEach(messageLog -> {
            if(messageLog.getTryCount() >= 3){
                //update fail message
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageLog.getMessageId(), Constants.ORDER_SEND_FAILURE, new Date());
            } else {
                // resend
                brokerMessageLogMapper.update4ReSend(messageLog.getMessageId(),  new Date());
                Order reSendOrder = FastJsonConvertUtil.convertJSONToObject(messageLog.getMessage(), Order.class);
                try {
                    rabbitOrderSender.sendOrder(reSendOrder);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("-----------异常处理-----------");
                }
            }
        });
    }
}

