package com.wulang.boot.redis.test;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author wulang
 * @create 2019/12/12/16:37
 */
@Slf4j
@Component
public class RedisCommandLineRunner implements CommandLineRunner {

    @Autowired
    @Qualifier("singleRedisConnection")
    private StatefulRedisConnection<String, String> connection;

    @Override
    public void run(String... args) throws Exception {
        RedisCommands<String, String> redisCommands = connection.sync();
        redisCommands.setex("name", 5, "wulang");
        log.info("Get value:{}", redisCommands.get("name"));
    }
}
