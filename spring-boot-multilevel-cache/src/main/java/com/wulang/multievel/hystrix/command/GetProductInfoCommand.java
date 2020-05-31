package com.wulang.multievel.hystrix.command;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.wulang.multievel.model.ProductInfo;

/**
 * 多重降级
 *
 * @author wulang
 * @create 2020/5/30/11:08
 */
public class GetProductInfoCommand extends HystrixCommand<ProductInfo> {

    private Long productId;

    public GetProductInfoCommand(Long productId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductService"))
            .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                .withCoreSize(10)
                .withMaximumSize(30)
                .withAllowMaximumSizeToDivergeFromCoreSize(true)
                .withKeepAliveTimeMinutes(1)
                .withMaxQueueSize(50)
                .withQueueSizeRejectionThreshold(100))
        );
        this.productId = productId;
    }

    @Override
    protected ProductInfo run() throws Exception {
        if (productId == 100) {
            // 我们在这里模拟的是说，从源头服务某个商品id没有查询到数据，我们这里写死了，比如就是proudctId=100
            // 在实际的生产环境中，我们其实是如果没有查询到数据，就给返回这么一个商品信息就可以了
            ProductInfo productInfo = new ProductInfo();
            productInfo.setId(productId);
            return productInfo;
        } else {
            // 发送http或rpc接口调用，去调用商品服务的接口
            String productInfoJSON = "{\"id\": " + productId + ", \"name\": \"iphone7手机\", \"price\": 5599, \"pictureList\":\"a.jpg,b.jpg\", \"specification\": \"iphone7的规格\", \"service\": \"iphone7的售后服务\", \"color\": \"红色,白色,黑色\", \"size\": \"5.5\", \"shopId\": 1, \"modifiedTime\": \"2017-01-01 12:01:00\"}";
            return JSONObject.parseObject(productInfoJSON, ProductInfo.class);
        }
    }

    @Override
    protected ProductInfo getFallback() {
        HBaseColdDataCommand command = new HBaseColdDataCommand(productId);
        return command.execute();
    }

    private class HBaseColdDataCommand extends HystrixCommand<ProductInfo> {

        private Long productId;

        public HBaseColdDataCommand(Long productId) {
            super(HystrixCommandGroupKey.Factory.asKey("HBaseGroup"));
            this.productId = productId;
        }

        @Override
        protected ProductInfo run() throws Exception {
            // 查询hbase  stubbed fallback
            String productInfoJSON = "{\"id\": " + productId + ", \"name\": \"iphone7手机\", \"price\": 5599, \"pictureList\":\"a.jpg,b.jpg\", \"specification\": \"iphone7的规格\", \"service\": \"iphone7的售后服务\", \"color\": \"红色,白色,黑色\", \"size\": \"5.5\", \"shopId\": 1, \"modifiedTime\": \"2017-01-01 12:01:00\"}";
            return JSONObject.parseObject(productInfoJSON, ProductInfo.class);
        }

        @Override
        protected ProductInfo getFallback() {
            ProductInfo productInfo = new ProductInfo();
            productInfo.setId(productId);
            // 从内存中找一些残缺的数据拼装进去
            return productInfo;
        }

    }

}
