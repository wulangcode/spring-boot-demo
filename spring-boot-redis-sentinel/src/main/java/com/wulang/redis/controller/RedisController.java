package com.wulang.redis.controller;

import com.wulang.redis.test1.RedisUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;


@Controller
public class RedisController {

//    @Autowired
//    StringRedisTemplate redisTemplate;

    @ResponseBody
    @RequestMapping("/test")
    public String test(){
        System.out.println("进入test方法 "+ LocalDateTime.now());
        int value= (int)(Math.random()*100);
        String v2="v"+value;
//        redisTemplate.opsForValue().set("name",v2);
//        String name = redisTemplate.opsForValue().get("name");
        RedisUtils.save("name",v2,80);
        String name = (String) RedisUtils.get("name");
        return name;
    }
}
