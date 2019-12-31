package com.wulang.boot.redis.test;

import com.wulang.boot.redis.po.Student;
import com.wulang.boot.redis.utils.IdWorker;
import com.wulang.boot.redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author wulang
 * @create 2019/11/24/12:22
 */
@Component
public class RedisTest {

    private static final String ACCESS = "access:";
    private static final String AUTH = "auth:";
    private static final String AUTH_TO_ACCESS = "auth_to_access:";


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Scheduled(cron = "*/5 * * * * ?")
    public void add(){
        Student student = new Student();
        IdWorker idWorker = new IdWorker();
        Random random = new Random();
        student.setId(idWorker.nextId());
        student.setName("随机名字" + idWorker.nextId());
        student.setScore(getRangeDate());
        /*
        redis 可以设置过期时间
        springcache 不能设置过期时间
         */
        redisTemplate.opsForValue().set(ACCESS + student.getName(), student,300, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(AUTH + student.getName(), student,200, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(AUTH_TO_ACCESS + student.getName(), student,100, TimeUnit.SECONDS);
        System.out.println("插入成功");
    }

    public int getRangeDate(){
        int max=100;
        int min=0;
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void get(){

        Student student = (Student) redisTemplate.opsForValue().get("access:随机名字1198493451847139329");
        System.out.println(student.toString());
    }
}
