package com.wulang.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wulang.api.IOrderService;
import com.wulang.entity.Result;
import com.wulang.shop.pojo.TradeOrder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wulang
 * @create 2019/12/27/16:58
 */
@RestController
@RequestMapping("/order")
public class OrderControllre {

    @Reference
    private IOrderService orderService;

    @RequestMapping("/confirm")
    public Result confirmOrder(@RequestBody TradeOrder order){
        return orderService.confirmOrder(order);
    }

}
