package com.wulang.boot;

import com.wulang.boot.redis.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring Cache使用方法与Spring对事务管理的配置相似。Spring Cache的核心就是对某
 * 个方法进行缓存，其实质就是缓存该方法的返回结果，并把方法参数和结果用键值对的
 * 方式存放到缓存中，当再次调用该方法使用相应的参数时，就会直接从缓存里面取出指
 * 定的结果进行返回。
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class RedisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisPlusApplication.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}
