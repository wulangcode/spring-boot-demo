package com.wulang.api;

import com.wulang.entity.Result;
import com.wulang.shop.pojo.TradePay;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 支付订单接口
 *
 * @author wulang
 * @create 2019/12/27/15:52
 */
public interface IPayService {

    public Result createPayment(TradePay tradePay);

    public Result callbackPayment(TradePay tradePay) throws InterruptedException, RemotingException, MQClientException, MQBrokerException;

}
