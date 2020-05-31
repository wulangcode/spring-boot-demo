package com.wulang.multievel.configuration.redis;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisCluster;

/**
 * 来源地址：
 * https://blog.csdn.net/u012099568/article/details/79664616
 *
 * @author wulang
 * @create 2020/5/14/11:44
 */
public class RedisClusterPool {
    public static GenericObjectPool<JedisCluster> objectPool = null;

    static {
        //工厂
        WlPoolFactory factory = new WlPoolFactory();
        //资源池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        //设置最大实例总数
        poolConfig.setMaxTotal(500);
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。 
        poolConfig.setMinIdle(1);
        poolConfig.setMaxIdle(1);
        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException； 
        poolConfig.setMaxWaitMillis(1000);
        // 在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的；  
        poolConfig.setTestOnBorrow(true);
        // 在还会给pool时，是否提前进行validate操作  
        poolConfig.setTestOnReturn(true);
        //如果为true，表示有一个idle object evitor线程对idle object进行扫描，如果validate失败，此object会被从pool中drop掉；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义；
        poolConfig.setTestWhileIdle(true);
        //表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义；
        poolConfig.setMinEvictableIdleTimeMillis(6000);
        //表示idle object evitor两次扫描之间要sleep的毫秒数
        poolConfig.setTimeBetweenEvictionRunsMillis(30000);
        //在minEvictableIdleTimeMillis基础上，加入了至少minIdle个对象已经在pool里面了。如果为-1，evicted不会根据idle time驱逐任何对象。如果minEvictableIdleTimeMillis>0，则此项设置无意义，且只有在timeBetweenEvictionRunsMillis大于0时才有意义
         poolConfig.setSoftMinEvictableIdleTimeMillis(-1);

        //创建资源池
        objectPool = new GenericObjectPool<JedisCluster>(factory, poolConfig);
    }

    @SuppressWarnings("finally")
    public static JedisCluster getJedisCluster() {
        JedisCluster jedisCluster = null;
        try {
            jedisCluster = objectPool.borrowObject();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            return jedisCluster;
        }

    }

    public static void closeJedisCluster(JedisCluster jedisCluster) {
        if (jedisCluster != null) {
            objectPool.returnObject(jedisCluster);
        }

    }
}
