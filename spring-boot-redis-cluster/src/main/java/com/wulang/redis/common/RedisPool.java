package com.wulang.redis.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author wulang
 * @create 2019/11/26/16:11
 */
public class RedisPool {
    private JedisPool pool;
    public RedisPool() {
        this.pool = new JedisPool();
    }
    public void execute(CallWithJedis caller) {
        try (Jedis jedis = pool.getResource()) {
            caller.call(jedis);
        }
    }
}
