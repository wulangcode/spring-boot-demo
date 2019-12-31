package com.wulang.boot.redis.alternative.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义redis配置(lettuce)
 *
 * @author wulang
 * @create 2019/12/16 0016/10:13
 */
@Configuration
@EnableCaching //启用缓存
public class LettuceAutoConfig extends CachingConfigurerSupport{
    private static final Logger LOGGER = LoggerFactory.getLogger(LettuceAutoConfig.class);

    /**
     * 自定义缓存key的生成策略。默认的生成策略是可读性较差的
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            /**
             *
             * @param target  当前被调用对象
             * @param method 当前被调用的方法
             * @param params 调用方法的参数列表
             * @return
             */
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName())
                        .append(":")
                        .append(method.getName());
                for (Object obj : params) {
                    sb.append(":")
                            .append(obj.toString());
                }
                LOGGER.info("自定义生成Redis Key -> [{}]", sb.toString());
                return sb.toString();
            }
        };
    }

    /**
     * 缓存配置管理器
     */
    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
        //以锁写入的方式创建RedisCacheWriter对象
        RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(lettuceConnectionFactory);
        //创建默认缓存配置对象
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        //设置默认超过期时间是30秒
        config.entryTtl(Duration.ofSeconds(30));
        //自定义cacheConfig
        Map<String, RedisCacheConfiguration> initialCacheConfigurations = new HashMap<>();
        for (Map.Entry<String, Integer> entry : CacheExpiresMap.get().entrySet()) {
            String key = entry.getKey();
            Integer seconds = entry.getValue();
            LOGGER.info("key{},value{}", key, seconds);
            RedisCacheConfiguration initialCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
            initialCacheConfigurations.put(key, initialCacheConfig.entryTtl(Duration.ofSeconds(seconds)));
        }
        //初始化RedisCacheManager
        RedisCacheManager cacheManager = new RedisCacheManager(writer, config,initialCacheConfigurations);
        return cacheManager;
    }

    /**
     * 手动配置注入redisTemplate
     * @param lettuceConnectionFactory lettuce连接工厂
     * @return {@link RedisTemplate}
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<Object,Object> setRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        RedisTemplate<Object,Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(lettuceConnectionFactory);
        //以下代码为将RedisTemplate的Value序列化方式由JdkSerializationRedisSerializer更换为Jackson2JsonRedisSerializer
        //此种序列化方式结果清晰、容易阅读、存储字节少、速度快，所以推荐更换
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //template.setEnableTransactionSupport(true);//是否启用事务
        template.afterPropertiesSet();
        return template;
    }

    @Override
    @Bean
    public CacheErrorHandler errorHandler() {
        // 异常处理，当Redis发生异常时，打印日志，但是程序正常走
        LOGGER.info("初始化 -> [{}]", "Redis CacheErrorHandler");
        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                LOGGER.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                LOGGER.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value, e);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key)    {
                LOGGER.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                LOGGER.error("Redis occur handleCacheClearError：", e);
            }
        };
        return cacheErrorHandler;
    }
}
