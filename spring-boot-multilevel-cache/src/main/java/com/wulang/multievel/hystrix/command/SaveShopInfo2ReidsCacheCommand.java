package com.wulang.multievel.hystrix.command;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.wulang.multievel.model.ShopInfo;
import com.wulang.multievel.utils.RedisClusterUtils;

/**
 * @author wulang
 * @create 2020/5/28/7:54
 */
public class SaveShopInfo2ReidsCacheCommand extends HystrixCommand<Boolean> {

    private ShopInfo shopInfo;

    public SaveShopInfo2ReidsCacheCommand(ShopInfo shopInfo) {
        super(HystrixCommandGroupKey.Factory.asKey("RedisGroup"));
        this.shopInfo = shopInfo;
    }

    @Override
    protected Boolean run() throws Exception {
//        JedisCluster jedisCluster = (JedisCluster) SpringContext.getApplicationContext()
//            .getBean("JedisClusterFactory");
        String key = "shop_info_" + shopInfo.getId();
//        jedisCluster.set(key, JSONObject.toJSONString(shopInfo));
        RedisClusterUtils.setString(key, JSONObject.toJSONString(shopInfo));
        return true;
    }

    @Override
    protected Boolean getFallback() {
        return false;
    }
}
