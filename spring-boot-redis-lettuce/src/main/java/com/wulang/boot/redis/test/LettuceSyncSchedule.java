package com.wulang.boot.redis.test;

import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 本类中方法为同步 API 使用
 *
 * @author wulang
 * @create 2019/12/12/17:15
 */
@Component
public class LettuceSyncSchedule {
    private RedisCommands<String,String> syncCommands;

    @Autowired
    public LettuceSyncSchedule(@Qualifier("syncCommand")RedisCommands<String,String> redisCommands) {
        this.syncCommands = redisCommands;
    }

    //@Scheduled(cron = "0 0 0 */3 * *")
    public void test(){
        syncCommands.set("key", "Hello, Redis!");
        String value = syncCommands.get("key");

        syncCommands.hset("recordName", "FirstName", "John");
        syncCommands.hset("recordName", "LastName", "Smith");
        Map<String, String> record = syncCommands.hgetall("recordName");


    }
}
