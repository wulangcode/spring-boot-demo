package com.wulang.boot.redis.config;

/**
 * @author wulang
 * @create 2019/11/24/8:32
 */

import com.wulang.boot.redis.utils.SpringCacheKeyGenerator;
import com.wulang.boot.redis.utils.SpringCacheResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@Configuration
@ComponentScan(basePackages = "com.wulang.boot.redis.service")
@EnableCaching(proxyTargetClass = true)
class SpringCacheConfig implements CachingConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringCacheConfig.class);

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
//    private RedisTemplate<String,Object> redisTemplate;

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        /**
         * 注意：springboot 1.5.x到2.0.x.当中的RedisCacheManager的实例化参数不一样
         */
//        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(20));
//                .entryTtl(Duration.ofHours(1)); // 设置缓存有效期一小时
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
//        rcm.setUsePrefix(true);
        //设置缓存过期时间
//        rcm.setDefaultExpiration(60);//秒，便于测试
//        return rcm;
    }

    @Override
    public CacheManager cacheManager() {
        return null;
    }

    @Bean("springCacheResolver")
    @Override
    public SpringCacheResolver cacheResolver() {
        return new SpringCacheResolver();
    }

    @Bean("springCacheKeyGenerator")
    @Override
    public SpringCacheKeyGenerator keyGenerator() {
        return new SpringCacheKeyGenerator();
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                LOGGER.warn("cache get error:" + key.toString(), exception);
            }

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                LOGGER.warn("cache put error:" + key.toString(), exception);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                LOGGER.warn("cache evict error:" + key.toString(), exception);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                LOGGER.warn("cache clear error:", exception);
            }
        };
    }
}
