package com.wulang.redis.test2;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @author wulang
 * @create 2019/11/27/21:38
 */
@Slf4j
public class JedisSentinelUtil {
    /**
     * 设置 key
     * @param key   键
     * @param value 值
     * @return
     */
    public static String set(String key, String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = SentinelPool.getJedis();
            result= jedis.set(key, value);
        } catch (Exception e) {
            log.error("set key:{}, error",key,e);
            SentinelPool.returnBrokenResource(jedis);
            return result;
        }
        SentinelPool.returnResource(jedis);
        return result;
    }


    /**
     * 设置一个有过期时间的 kv
     * @param key
     * @param value
     * @param exTime
     * @return
     */
    public static String setEx(String key, String value,int exTime) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = SentinelPool.getJedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("setex key:{}, error",key,e);
            SentinelPool.returnBrokenResource(jedis);
            return result;
        }
        SentinelPool.returnResource(jedis);
        return result;
    }

    /**
     * key 不存在就设置
     * @param key
     * @param value
     * @return
     */
    public static Long setNx(String key, String value) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = SentinelPool.getJedis();
            result= jedis.setnx(key, value);
        } catch (Exception e) {
            log.error("setnx key:{}, error",key,e);
            SentinelPool.returnBrokenResource(jedis);
            return result;
        }
        SentinelPool.returnResource(jedis);
        return result;
    }
    /**
     * 删除 key
     * @param key
     * @return
     */
    public static Long del(String key) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = SentinelPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("del key {}, error",key,e);
            SentinelPool.returnBrokenResource(jedis);
            return result;
        }
        SentinelPool.returnResource(jedis);
        return result;
    }

    /**
     * 获取 Key 对应的值
     * @param key
     * @return
     */
    public static String get(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = SentinelPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key {}, error",key,e);
            SentinelPool.returnBrokenResource(jedis);
            return result;
        }
        SentinelPool.returnResource(jedis);
        return result;
    }


    /**
     * 设置一个新的值，并返回旧的值
     * @param key
     * @param value
     * @return
     */
    public static String getSet(String key,String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = SentinelPool.getJedis();
            result = jedis.getSet(key, value);
        } catch (Exception e) {
            log.error("getSet key {}, error",key,e);
            SentinelPool.returnBrokenResource(jedis);
            return result;
        }
        SentinelPool.returnResource(jedis);
        return result;
    }


    /**
     * 设置过期时间
     * @param key
     * @param exTime
     * @return
     */
    public static Long expire(String key, int exTime) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = SentinelPool.getJedis();
            result= jedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("expire key:{}, error",key,e);
            SentinelPool.returnBrokenResource(jedis);
            return result;
        }
        SentinelPool.returnResource(jedis);
        return result;
    }
    /**
     * 获取 dbsize
     * @return
     */
    public static Long getDbSize() {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = SentinelPool.getJedis();
            result = jedis.dbSize();
        } catch (Exception e) {
            log.error("get dbSize, error",e);
            SentinelPool.returnBrokenResource(jedis);
            return result;
        }
        SentinelPool.returnResource(jedis);
        return result;
    }
}
