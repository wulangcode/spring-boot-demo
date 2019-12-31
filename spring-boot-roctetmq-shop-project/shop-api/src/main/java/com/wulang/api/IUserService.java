package com.wulang.api;

import com.wulang.entity.Result;
import com.wulang.shop.pojo.TradeUser;
import com.wulang.shop.pojo.TradeUserMoneyLog;

/**
 * @author wulang
 * @create 2019/12/27/10:04
 */
public interface IUserService {
    TradeUser findOne(Long userId);

    Result updateMoneyPaid(TradeUserMoneyLog userMoneyLog);
}
