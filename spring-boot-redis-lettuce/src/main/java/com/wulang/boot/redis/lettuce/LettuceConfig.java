package com.wulang.boot.redis.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LettuceConfig {

    private static final String BOOT = "xxx";

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String password;
    @Bean(destroyMethod = "shutdown")
    ClientResources clientResources() {
        return DefaultClientResources.create();
    }

    @Bean(destroyMethod = "shutdown")
    RedisClient redisClient(ClientResources clientResources) {
        return RedisClient.create(clientResources,  RedisURI.Builder.redis(host,port).withPassword(password).build());
    }

    @Bean(destroyMethod = "close")
    StatefulRedisConnection<String, String> connection(RedisClient redisClient) {
        return redisClient.connect();
    }

    @Bean("syncCommand")
    RedisCommands<String, String> redisSyncCommands(StatefulRedisConnection<String,String> connection) {
        return connection.sync();
    }

    @Bean("asyncCommand")
    RedisAsyncCommands<String, String> redisAsyncCommands(StatefulRedisConnection<String,String> connection) {
        return connection.async();
    }

    @Bean("reactiveCommand")
    RedisReactiveCommands<String, String> redisReactiveCommands(StatefulRedisConnection<String,String> connection) {
        return connection.reactive();
    }

    @Bean("pubSubCommand")
    RedisPubSubCommands<String, String> pubSubConnection(){
        DefaultClientResources defaultClientResources = DefaultClientResources.create();
        RedisClient redisClient = RedisClient.create(defaultClientResources, RedisURI.Builder.redis(host, port).withPassword(password).build());
        StatefulRedisPubSubConnection<String, String> pubSubConnection = redisClient.connectPubSub();
        pubSubConnection.addListener(new MessageReceiver());
        RedisPubSubCommands<String, String> sync = pubSubConnection.sync();
        sync.subscribe(BOOT);
        return sync;
    }


}
