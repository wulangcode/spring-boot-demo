package com.wulang.multievel.hystrix.command;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.wulang.multievel.model.ProductInfo;
import com.wulang.multievel.utils.RedisClusterUtils;

/**
 * 将商品信息保存到redis中 的 线程资源池
 *
 * @author wulang
 * @create 2020/5/28/7:39
 */
public class SaveProductInfo2ReidsCacheCommand extends HystrixCommand<Boolean> {

    private ProductInfo productInfo;

    public SaveProductInfo2ReidsCacheCommand(ProductInfo productInfo) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("RedisGroup"))
            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                //timeout超时控制
                .withExecutionTimeoutInMilliseconds(100)
                //设置一个rolling window，滑动窗口中，最少要有多少个请求时，才触发开启短路
                .withCircuitBreakerRequestVolumeThreshold(1000)
                //设置异常请求量的百分比，当异常请求达到这个百分比时，就触发打开短路器，默认是50，也就是50%
                .withCircuitBreakerErrorThresholdPercentage(70)
                //设置在短路之后，需要在多长时间内直接reject请求，然后在这段时间之后，再重新导holf-open状态，
                // 尝试允许请求通过以及自动恢复，默认值是5000毫秒
                .withCircuitBreakerSleepWindowInMilliseconds(60 * 1000))
        );
        this.productInfo = productInfo;
    }

    @Override
    protected Boolean run() throws Exception {
        String key = "product_info_" + productInfo.getId();
        RedisClusterUtils.setString(key, JSONObject.toJSONString(productInfo));
        return true;
    }

    @Override
    protected Boolean getFallback() {
        return false;
    }
}
