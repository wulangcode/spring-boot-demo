package com.wulang.redis.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * 我们知道 Jedis 默认没有提供重试机制，意味着如果网络出现了抖动，
 * 就会大范围报错，或者一个后台应用因为链接过于空闲被服务端强制关闭了链接，
 * 当重新发起新请求时就第一个指令会出错
 *
 * @author wulang
 * @create 2019/11/26/16:25
 */
public class Redis {
    private JedisPool pool;
    public Redis() {
        this.pool = new JedisPool();
    }
    public void execute(CallWithJedis caller) {
        Jedis jedis = pool.getResource();
        try {
            caller.call(jedis);
        } catch (JedisConnectionException e) {
            caller.call(jedis); // 重试一次
        } finally {
            jedis.close();
        }
    }
}
