package com.wulang.redis.controller;

import com.alibaba.fastjson.JSON;
import com.wulang.redis.po.Student;
import com.wulang.redis.test1.RedisClusterPool;
import com.wulang.redis.utils.IdWorker;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import java.time.LocalDateTime;
import java.util.Random;

@Controller
public class RedisController {

    private static Log logger = LogFactory.getLog(RedisController.class);

    @ResponseBody
    @RequestMapping("/test")
    public Student test(){
        System.out.println("进入test方法 "+ LocalDateTime.now());
        int value= (int)(Math.random()*100);
        Student student = new Student();
        IdWorker idWorker = new IdWorker();
        Random random = new Random();
        student.setId(idWorker.nextId());
        String name = "随机名字" + idWorker.nextId();
        student.setName(name);
        student.setScore(getRangeDate());
        JedisCluster jedisCluster = RedisClusterPool.getJedisCluster();
        jedisCluster.sadd(name,JSON.toJSONString(student));
        String s = jedisCluster.get(name);
        Student s1 = JSON.parseObject(s,Student.class);
        RedisClusterPool.closeJedisCluster(jedisCluster);
        return s1;
    }

    public int getRangeDate(){
        int max=100;
        int min=0;
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }
}
