package com.wulang.multievel.hystrix.command;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.wulang.multievel.model.ShopInfo;
import com.wulang.multievel.utils.RedisClusterUtils;

/**
 * @author wulang
 * @create 2020/5/28/7:56
 */
public class GetShopInfoFromReidsCacheCommand extends HystrixCommand<ShopInfo> {

    private Long shopId;

    public GetShopInfoFromReidsCacheCommand(Long shopId) {
        super(HystrixCommandGroupKey.Factory.asKey("RedisGroup"));
        this.shopId = shopId;
    }

    @Override
    protected ShopInfo run() throws Exception {
        String key = "shop_info_" + shopId;
        String json = RedisClusterUtils.getString(key);
        if(json != null) {
            return JSONObject.parseObject(json, ShopInfo.class);
        }
        return null;
    }

    @Override
    protected ShopInfo getFallback() {
        return null;
    }
}
