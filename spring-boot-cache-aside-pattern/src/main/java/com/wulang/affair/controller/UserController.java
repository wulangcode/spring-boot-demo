package com.wulang.affair.controller;

import com.alibaba.fastjson.JSON;
import com.wulang.affair.config.redis.RedisClusterPool;
import com.wulang.affair.model.ProductInventory;
import com.wulang.affair.model.User;
import com.wulang.affair.service.UserService;
import com.wulang.affair.utils.RedisClusterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisCluster;

import java.util.Date;

/**
 * 用户Controller控制器
 *
 * @author Administrator
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class UserController {

    static String INVENTORY = "product:inventory:";

    @Autowired
    private UserService userService;

    @PostMapping("/getUserInfo")
    @ResponseBody
    public User getUserInfo(@RequestBody User user) {
        log.info("获取请求队列" + new Date());
//        User user = userService.findUserInfo();
        String name = user.getName();
        Integer age = user.getAge();
        User user1 = new User();
        user1.setAge(age + 1);
        user1.setName(name + "test");
        return user1;
    }

    @RequestMapping("/getCachedUserInfo")
    @ResponseBody
    public User getCachedUserInfo() {
        User user = userService.getCachedUserInfo();
        return user;
    }

    @GetMapping("/testRedis")
    public User testRedisUser(){
        JedisCluster jedisCluster = RedisClusterPool.getJedisCluster();
        User user = new User();
        user.setName("userName: test1");
        user.setAge(10);
        jedisCluster.set(user.getName(), JSON.toJSONString(user));
        String s = jedisCluster.get(user.getName());
        User user1 = JSON.parseObject(s, User.class);
        return user1;
    }

    /**
     *
     */
    @GetMapping("/test3")
    public void test3(){
        String key = INVENTORY + 456;
        String s = RedisClusterUtils.setString(key, String.valueOf(456));
        System.out.println(s);
        String string = RedisClusterUtils.getString(key);
        System.out.println(string);
        ProductInventory productInventory = new ProductInventory();
        productInventory.setProductId(987654);
        productInventory.setInventoryCnt(987654L);
        String s1 = RedisClusterUtils.setString(key, String.valueOf(productInventory.getInventoryCnt()));
        System.out.println(s1);
    }
}
