package com.wulang.api;

import com.wulang.entity.Result;
import com.wulang.shop.pojo.TradeGoods;
import com.wulang.shop.pojo.TradeGoodsNumberLog;
/**
 * @author wulang
 * @create 2019/12/27/10:00
 */
public interface IGoodsService {
    /**
     * 根据ID查询商品对象
     * @param goodsId
     * @return
     */
    TradeGoods findOne(Long goodsId);

    /**
     * 扣减库存
     * @param goodsNumberLog
     * @return
     */
    Result reduceGoodsNum(TradeGoodsNumberLog goodsNumberLog);
}
