package com.wulang.affair.hystrix.command;

import com.netflix.hystrix.*;
import com.wulang.affair.local.LocationCache;

/**
 * 信号量的隔离
 *
 * @author wulang
 * @create 2020/5/23/12:00
 */
public class GetCityNameCommand extends HystrixCommand<String> {

    private Long cityId;

    public GetCityNameCommand(Long cityId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetCityNameGroup"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("GetCityNameCommand"))
            .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetCityNamePool"))
            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                //信号量的隔离
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                //设置使用SEMAPHORE隔离策略的时候，允许访问的最大并发量，超过这个最大并发量，请求直接被reject
                .withExecutionIsolationSemaphoreMaxConcurrentRequests(15)));
        this.cityId = cityId;
    }

    @Override
    protected String run() throws Exception {
        return LocationCache.getCityName(cityId);
    }
}
