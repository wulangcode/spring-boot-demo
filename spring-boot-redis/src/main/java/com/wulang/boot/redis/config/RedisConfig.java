package com.wulang.boot.redis.config;

import com.wulang.boot.redis.utils.RedisObjectSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 作者 owen E-mail: 624191343@qq.com
 * @version 创建时间：2017年04月23日 下午20:01:06 类说明
 */
@Configuration
public class RedisConfig {

//    {
//        @Retention(RetentionPolicy.RUNTIME)
//        @Target({ ElementType.TYPE, ElementType.METHOD })
//        @Documented
//        @Conditional(OnPropertyCondition.class)
//        public @interface ConditionalOnProperty {
//            /**
//             * 数组，获取对应property名称的值，与name不可同时使用
//             */
//            String[] value() default {};
//            /**
//             * property名称的前缀，可有可无
//             */
//            String prefix() default "";
//            /**
//             * 数组，property完整名称或部分名称（可与prefix组合使用，组成完整的property名称），与value不可同时使用
//             */
//            String[] name() default {};
//            /**
//             * 可与name组合使用，比较获取到的属性值与havingValue给定的值是否相同，相同才加载配置
//             */
//            String havingValue() default "";
//            /**
//             * 缺少该property时是否可以加载。如果为true，没有该property也会正常加载；反之报错
//             */
//            boolean matchIfMissing() default false;
//            /**
//             * 是否可以松散匹配，至今不知道怎么使用的
//             */
//            boolean relaxedNames() default true;
//        }
//    }


    @Primary
    @Bean("redisTemplate")
    // 没有此属性就不会装配bean 如果是单个redis 将此注解注释掉
    @ConditionalOnProperty(name = "spring.redis.cluster.nodes", matchIfMissing = false)
    public RedisTemplate<String, Object> getRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(factory);

        RedisSerializer stringSerializer = new StringRedisSerializer();
        // RedisSerializer redisObjectSerializer = new RedisObjectSerializer();
        RedisSerializer redisObjectSerializer = new RedisObjectSerializer();
        redisTemplate.setKeySerializer(stringSerializer); // key的序列化类型
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(redisObjectSerializer); // value的序列化类型
        redisTemplate.afterPropertiesSet();

        redisTemplate.opsForValue().set("hello", "wolrd");
        return redisTemplate;
    }

    @Primary
    @Bean("redisTemplate")
    @ConditionalOnProperty(name = "spring.redis.host", matchIfMissing = true)
    public RedisTemplate<String, Object> getSingleRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // key的序列化类型
        redisTemplate.setValueSerializer(new RedisObjectSerializer()); // value的序列化类型
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setBlockWhenExhausted(true);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMaxIdle(500);
        jedisPoolConfig.setMinIdle(300);
        jedisPoolConfig.setMaxWaitMillis(100000);
        return jedisPoolConfig;
    }
}


