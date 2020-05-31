package com.wulang.affair.hystrix.command;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.*;
import com.wulang.affair.local.BrandCache;
import com.wulang.affair.local.LocationCache;
import com.wulang.affair.model.ProductInfo;
import com.wulang.affair.utils.HttpClientUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取品牌名称的command
 * <p>
 * HystrixCommand：是用来获取一条数据的
 * HystrixObservableCommand：是设计用来获取多条数据的
 * <p>
 * 多重降级
 *
 * @author wulang
 * @create 2020/5/23/10:24
 */
public class GetProductInfoCommand extends HystrixCommand<ProductInfo> {

    private Long productId;

    public GetProductInfoCommand(Long productId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductInfoService"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("GetProductInfoCommand"))
            .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetProductInfoPool"))
            .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                //设置线程池的大小，默认是10
                .withCoreSize(10)
                //设置线程池的最大大小，只有在设置allowMaximumSizeToDivergeFromCoreSize的时候才能生效,默认是10
                .withMaximumSize(30)
                //允许线程池大小自动动态调整，设置为true之后，maxSize就生效了，此时如果一开始是coreSize个线程，
                // 随着并发量上来，那么就会自动获取新的线程，但是如果线程在keepAliveTimeMinutes内空闲，就会被自动释放掉,默认是fales
                .withAllowMaximumSizeToDivergeFromCoreSize(true)
                //设置保持存活的时间，单位是分钟，默认是1
                .withKeepAliveTimeMinutes(1)
                /**
                 * 如果withMaxQueueSize<withQueueSizeRejectionThreshold，那么取的是withMaxQueueSize，
                 * 反之，取得是withQueueSizeRejectionThreshold
                 */
                //设置的是你的等待队列，缓冲队列的大小
                .withMaxQueueSize(12)
                //控制queue满后reject的threshold，因为maxQueueSize不允许热修改，因此提供这个参数可以热修改，控制队列的最大大小
                .withQueueSizeRejectionThreshold(15))
            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                //设置一个rolling window，滑动窗口中，最少要有多少个请求时，才触发开启短路
                //举例来说，如果设置为20（默认值），那么在一个10秒的滑动窗口内，如果只有19个请求，即使这19个请求都是异常的，也是不会触发开启短路器的
                .withCircuitBreakerRequestVolumeThreshold(30)
                //设置异常请求量的百分比，当异常请求达到这个百分比时，就触发打开短路器，默认是50，也就是50%
                .withCircuitBreakerErrorThresholdPercentage(40)
                //会half-open，让一条请求经过短路器，看能不能正常调用。如果调用成功了，那么就自动恢复，转到close状态
                .withCircuitBreakerSleepWindowInMilliseconds(3000)
                //执行超时（以毫秒为单位）
                //timeout也设置大一些，否则如果请求放等待队列中时间太长了，直接就会timeout，等不到去线程池里执行了
                .withExecutionTimeoutInMilliseconds(500)
                //这个参数设置了HystrixCommand.getFallback()最大允许的并发请求数量，默认值是10，也是通过semaphore信号量的机制去限流
                .withFallbackIsolationSemaphoreMaxConcurrentRequests(30))
        );
        this.productId = productId;
    }

    @Override
    protected ProductInfo run() throws Exception {
//		System.out.println("调用接口，查询商品数据，productId=" + productId);
//
        if (productId.equals(-1L)) {
            throw new Exception();
        }
        if (productId.equals(-2L)) {
            throw new Exception();
        }
//
//		if(productId.equals(-2L)) {
//			Thread.sleep(3000);
//		}
//
//		if(productId.equals(-3L)) {
////			Thread.sleep(250);
//		}

        String url = "http://127.0.0.1:8082/getProductInfo?productId=" + productId;
        String response = HttpClientUtils.sendGetRequest(url);
        return JSONObject.parseObject(response, ProductInfo.class);
    }

//    @Override
//	protected String getCacheKey() {
//		return "product_info_" + productId;
//	}

    @Override
    protected ProductInfo getFallback() {
        return new FirstLevelFallbackCommand(productId).execute();
    }

    private static class FirstLevelFallbackCommand extends HystrixCommand<ProductInfo> {

        private Long productId;

        public FirstLevelFallbackCommand(Long productId) {
            // 第一级的降级策略，因为这个command是运行在fallback中的
            // 所以至关重要的一点是，在做多级降级的时候，要将降级command的线程池单独做一个出来
            // 如果主流程的command都失败了，可能线程池都已经被占满了
            // 降级command必须用自己的独立的线程池
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductInfoService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("FirstLevelFallbackCommand"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("FirstLevelFallbackPool"))
            );
            this.productId = productId;
        }

        @Override
        protected ProductInfo run() throws Exception {
            // 这里，因为是第一级降级的策略，所以说呢，其实是要从备用机房的机器去调用接口
            // 但是，我们这里没有所谓的备用机房，所以说还是调用同一个服务来模拟
            if (productId.equals(-2L)) {
                throw new Exception();
            }
            String url = "http://127.0.0.1:8082/getProductInfo?productId=" + productId;
            String response = HttpClientUtils.sendGetRequest(url);
            return JSONObject.parseObject(response, ProductInfo.class);
        }

        @Override
        protected ProductInfo getFallback() {
            // 第二级降级策略，第一级降级策略，都失败了
            ProductInfo productInfo = new ProductInfo();
            // 从请求参数中获取到的唯一条数据
            productInfo.setId(productId);
            // 从本地缓存中获取一些数据
            productInfo.setBrandId(BrandCache.getBrandId(productId));
            productInfo.setBrandName(BrandCache.getBrandName(productInfo.getBrandId()));
            productInfo.setCityId(LocationCache.getCityId(productId));
            productInfo.setCityName(LocationCache.getCityName(productInfo.getCityId()));
            // 手动填充一些默认的数据
            productInfo.setColor("默认颜色");
            productInfo.setModifiedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            productInfo.setName("默认商品");
            productInfo.setPictureList("default.jpg");
            productInfo.setPrice(0.0);
            productInfo.setService("默认售后服务");
            productInfo.setShopId(-1L);
            productInfo.setSize("默认大小");
            productInfo.setSpecification("默认规格");
            return productInfo;
        }

    }

//	public static void flushCache(Long productId) {
//		HystrixRequestCache.getInstance(KEY,
//                HystrixConcurrencyStrategyDefault.getInstance()).clear("product_info_" + productId);
//	}

}
