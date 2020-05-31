package com.wulang.multievel.utils;

import com.wulang.multievel.configuration.redis.RedisClusterPool;
import redis.clients.jedis.JedisCluster;

/**
 * @author wulang
 * @create 2020/5/14/12:02
 */
public class RedisClusterUtils {

    /**
     * 向集群中set String类型操作
     *
     * @param key
     * @param value
     * @return
     */
    public static String setString(String key, String value) {
        JedisCluster jedisCluster = RedisClusterPool.getJedisCluster();
        String set = jedisCluster.set(key, value);
        RedisClusterPool.closeJedisCluster(jedisCluster);
        return set;
    }

    /**
     * 根据key获得value
     *
     * @param key 可以传一个值 或多个
     * @return
     */
    public static String getString(String key) {
        JedisCluster jedisCluster = RedisClusterPool.getJedisCluster();
        String s = jedisCluster.get(key);
        RedisClusterPool.closeJedisCluster(jedisCluster);
        return s;
    }

    /**
     * 删除缓存
     *
     * @param key
     * @return
     */
    public static Long delString(String key) {
        JedisCluster jedisCluster = RedisClusterPool.getJedisCluster();
        Long del = jedisCluster.del(key);
        RedisClusterPool.closeJedisCluster(jedisCluster);
        return del;
    }
}
