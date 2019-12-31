package com.wulang.api;

import com.wulang.entity.Result;
import com.wulang.shop.pojo.TradeCoupon;

/**
 * 优惠券接口
 *
 * @author wulang
 * @create 2019/12/27/10:41
 */
public interface ICouponService {


    /**
     * 根据ID查询优惠券对象
     * @param coupouId
     * @return
     */
    public TradeCoupon findOne(Long coupouId);

    /**
     * 更细优惠券状态
     * @param coupon
     * @return
     */
    Result updateCouponStatus(TradeCoupon coupon);
}

