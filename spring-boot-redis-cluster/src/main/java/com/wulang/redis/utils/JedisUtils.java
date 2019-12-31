package com.wulang.redis.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author wulang
 * @create 2019/11/26/14:57
 */
public class JedisUtils {
    private static Log logger = LogFactory.getLog(JedisUtils.class);

    /**
     * 自动注入Redis连接实例对象线程池
     */
    @Autowired
    private JedisPool jedisPool;

    /**
     * 获取Jedis对象
     *
     * @return
     */
    public synchronized Jedis getJedis() {
        Jedis jedis = null;
        if (jedisPool != null) {
            try {
                if (jedis == null) {
                    jedis = jedisPool.getResource();
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return jedis;
    }

    /**
     * 回收Jedis对象资源
     *
     * @param jedis
     */
    public synchronized void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Jedis对象出异常的时候，回收Jedis对象资源
     *
     * @param jedis
     */
    public synchronized void returnBrokenResource(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnBrokenResource(jedis);
        }

    }
}
