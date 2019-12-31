package com.wulang.shop.mq;

import com.alibaba.fastjson.JSON;
import com.wulang.constant.ShopCode;
import com.wulang.shop.mapper.TradeOrderMapper;
import com.wulang.shop.pojo.TradeOrder;
import com.wulang.shop.pojo.TradePay;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * 支付成功后，支付服务payService发送MQ消息，订单服务、用户服务、日志服务需要订阅消息进行处理
 *
 * 1.订单服务修改订单状态为已支付
 * 2.日志服务记录支付日志
 * 3.用户服务负责给用户增加积分
 *
 * 以下用订单服务为例说明消息的处理情况
 *
 * @author wulang
 * @create 2019/12/27/16:37
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "${mq.pay.topic}",consumerGroup = "${mq.pay.consumer.group.name}",messageModel = MessageModel.BROADCASTING)
public class PaymentListener implements RocketMQListener<MessageExt> {

    @Autowired
    private TradeOrderMapper orderMapper;

    @Override
    public void onMessage(MessageExt messageExt) {

        log.info("接收到支付成功消息");

        try {
            //1.解析消息内容
            String body = new String(messageExt.getBody(),"UTF-8");
            TradePay tradePay = JSON.parseObject(body,TradePay.class);
            //2.根据订单ID查询订单对象
            TradeOrder tradeOrder = orderMapper.selectByPrimaryKey(tradePay.getOrderId());
            //3.更改订单支付状态为已支付
            tradeOrder.setPayStatus(ShopCode.SHOP_ORDER_PAY_STATUS_IS_PAY.getCode());
            //4.更新订单数据到数据库
            orderMapper.updateByPrimaryKey(tradeOrder);
            log.info("更改订单支付状态为已支付");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}

