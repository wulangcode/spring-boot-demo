package com.wulang.redis.controller;

import com.wulang.redis.po.Student;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Controller
public class RedisController {

    private static Log logger = LogFactory.getLog(RedisController.class);



    public static final int TIMEOUT = 3000; //连接超时时间
    public static final int SO_TIMEOUT = 100; //间隔超时时间
    public static final int MAX_ATTEMPTS = 100; //重试次数
    public static final String REDIS_AUTH = "123456"; //Redis数据库访问密码
    //
    public static final int MAX_TOTAL = 1000; //最大连接数
    public static final int MAX_IDEL = 200; // 空闲连接数
    public static final int MAX_WAIT_MILLIS = 1000; //最大等待时间
    public static final boolean TEST_ON_BORROW = true;// 进行连接测试，以保证返回的连接为可用连接

    @ResponseBody
    @RequestMapping("/test")
    public Student test(){
        System.out.println("进入test方法 "+ LocalDateTime.now());
//        int value= (int)(Math.random()*100);
//        Student student = new Student();
//        IdWorker idWorker = new IdWorker();
//        Random random = new Random();
//        student.setId(idWorker.nextId());
//        String name = "随机名字" + idWorker.nextId();
//        student.setName(name);
//        student.setScore(getRangeDate());
//        JedisCluster jedisCluster = RedisClusterPool.getJedisCluster();
//        jedisCluster.sadd(name,JSON.toJSONString(student));
//        String s = jedisCluster.get(name);
//        Student s1 = JSON.parseObject(s,Student.class);
//        RedisClusterPool.closeJedisCluster(jedisCluster);


        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(MAX_TOTAL);// 最大连接数
        poolConfig.setMaxIdle(MAX_IDEL); // 空闲连接数(最小维持连接数)
        poolConfig.setMaxWaitMillis(MAX_WAIT_MILLIS);// 最大等待时间
        poolConfig.setTestOnBorrow(TEST_ON_BORROW);
        //
        Set<HostAndPort> clusters = new HashSet<HostAndPort>();
        clusters.add(new HostAndPort("122.152.197.167", 6379));
        clusters.add(new HostAndPort("122.152.197.167", 6380));
        clusters.add(new HostAndPort("122.152.197.167", 6381));
        clusters.add(new HostAndPort("122.152.197.167", 6382));
        clusters.add(new HostAndPort("122.152.197.167", 6383));
        clusters.add(new HostAndPort("122.152.197.167", 6384));

        //创建RedisCluster集群访问对象
        JedisCluster jedis= new JedisCluster(clusters, TIMEOUT, SO_TIMEOUT, MAX_ATTEMPTS, REDIS_AUTH, poolConfig);
        jedis.set("id", "1002");
        jedis.set("name", "zhangsan2");
        jedis.set("level", "2");
        System.out.println(jedis.get("id") + ", " + jedis.get("name") + ", " + jedis.get("level"));
        try {
            jedis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getRangeDate(){
        int max=100;
        int min=0;
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }
}
