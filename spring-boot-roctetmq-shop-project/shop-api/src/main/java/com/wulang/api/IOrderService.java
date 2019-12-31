package com.wulang.api;

import com.wulang.entity.Result;
import com.wulang.shop.pojo.TradeOrder;

/**
 * @author wulang
 * @create 2019/12/27/9:52
 */
public interface IOrderService {
    /**
     * 确认订单
     * @param order
     * @return Result
     */
    Result confirmOrder(TradeOrder order);
}
