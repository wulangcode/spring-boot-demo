package com.wulang.boot.redis.lettuce;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wulang.boot.redis.utils.HttpUtils;
import io.lettuce.core.pubsub.RedisPubSubAdapter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Redis提供简单的发布/订阅消息传递系统。订阅者使用subscribe命令使用来自通道的消息。邮件不会保留; 它们仅在订阅频道时发送给用户。
 *
 * Redis使用pub/sub系统获取有关Redis数据集的通知，使客户端能够接收有关设置，删除，过期等密钥的事件。
 *
 * RedisPubSubListener接收发布/订阅消息
 * @author wulang
 * @create 2019/12/12/17:00
 */
@Component
@Log4j2
public class MessageReceiver extends RedisPubSubAdapter<String,String> {

    private static final String BOOT = "xxx";

    @Value("${spring.rabbitmq.msg.http-url}")
    private String httpUrl;
    
    @Override
    public void message(String channel, String message) {
        if (BOOT.equals(channel)) {
            //TODO  自己的业务逻辑处理
            if (BOOT.equals(channel)) {
                JSONObject jsonObject = JSONObject.parseObject(message);
                String testId = jsonObject.getString("testId");
                String testName = jsonObject.getString("testName");
                JSONArray userIds = jsonObject.getJSONArray("userIds");
                Map<String, String> map = new HashMap<>(10);
                map.put("result", String.valueOf(true));
                map.put("id", testId);
                map.put("name", testName);
                String jsonString = JSON.toJSONString(map);
                System.out.println(httpUrl);
                userIds.forEach(id -> {
                    HashMap<String, String> useMap = new HashMap<>(2);
                    useMap.put("userId", id.toString());
                    useMap.put("message", jsonString);
                    System.out.println(id);
                    HttpUtils.sendPost(httpUrl,(Integer) id,useMap);
                });

            }
        }
    }

}
