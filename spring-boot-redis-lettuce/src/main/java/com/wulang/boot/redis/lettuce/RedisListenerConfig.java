package com.wulang.boot.redis.lettuce;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
/**
 * @author wulang
 * @create 2019/12/13/10:37
 */
@Configuration
public class RedisListenerConfig {

    private static final String BOOT = "xxx";
    private static final String METHOD = "resolve";


    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic(BOOT));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(MessageReceiver redisReceiver) {
        return new MessageListenerAdapter(redisReceiver, METHOD);
    }
}
