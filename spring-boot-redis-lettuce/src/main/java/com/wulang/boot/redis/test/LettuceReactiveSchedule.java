package com.wulang.boot.redis.test;

import io.lettuce.core.api.reactive.RedisReactiveCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import reactor.core.publisher.Mono;

/**
 * 非阻塞的反应API
 *
 * @author wulang
 * @create 2019/12/13/9:50
 */
public class LettuceReactiveSchedule {
    private RedisReactiveCommands<String,String> reactiveCommand;

    @Autowired
    public LettuceReactiveSchedule(@Qualifier("reactiveCommand") RedisReactiveCommands<String,String> redisCommands) {
        this.reactiveCommand = redisCommands;
    }

    //@Scheduled(cron = "0 0 0 */3 * *")
    public void reactiveTest(){
        //这些命令从Project Reactor返回包含在Mono或Flux中的结果
        Mono<String> mono = reactiveCommand.get("key");
    }
}
