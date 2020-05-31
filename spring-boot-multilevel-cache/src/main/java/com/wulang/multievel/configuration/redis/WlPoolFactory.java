package com.wulang.multievel.configuration.redis;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author wulang
 * @create 2020/5/14/11:42
 */
public class WlPoolFactory implements PooledObjectFactory<JedisCluster> {

    /**
     * 连接超时时间
     */
    public static final int TIMEOUT = 3000;
    /**
     * 间隔超时时间
     */
    public static final int SO_TIMEOUT = 100;
    /**
     * 重试次数
     */
    public static final int MAX_ATTEMPTS = 100;
    /**
     * Redis数据库访问密码
     */
    public static final String REDIS_AUTH = "123456";
    /**
     * 最大连接数
     */
    public static final int MAX_TOTAL = 1000;
    /**
     * 空闲连接数
     */
    public static final int MAX_IDEL = 200;
    /**
     * 最大等待时间
     */
    public static final int MAX_WAIT_MILLIS = 1000;
    /**
     * 进行连接测试，以保证返回的连接为可用连接
     */
    public static final boolean TEST_ON_BORROW = true;

    /**
      * 功能描述：激活资源对象
      * 
      * 什么时候会调用此方法
      * 1：从资源池中获取资源的时候
      * 2：资源回收线程，回收资源的时候，根据配置的 testWhileIdle 参数，
      * 判断 是否执行 factory.activateObject()方法，true 执行，false 不执行
      * @param arg0 
      */
    @Override
    public void activateObject(PooledObject<JedisCluster> arg0) throws Exception {
//        System.out.println("activate Object");
    }

    @Override
    public void destroyObject(PooledObject<JedisCluster> arg0) throws Exception {
//        System.out.println("destroy Object");
        JedisCluster JedisCluster = arg0.getObject();
        JedisCluster = null;
    }


    @Override
    public PooledObject<JedisCluster> makeObject() throws Exception {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 最大连接数  
        poolConfig.setMaxTotal(300);
        // 最大空闲数  
        poolConfig.setMaxIdle(10);
        // 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：  
        // Could not get a resource from the pool  
        poolConfig.setMaxWaitMillis(1000);

        Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
        nodes.add(new HostAndPort("122.152.197.167", 6379));
        nodes.add(new HostAndPort("122.152.197.167", 6380));
        nodes.add(new HostAndPort("122.152.197.167", 6381));
        nodes.add(new HostAndPort("122.152.197.167", 6382));
        nodes.add(new HostAndPort("122.152.197.167", 6383));
        nodes.add(new HostAndPort("122.152.197.167", 6384));
        JedisCluster JedisCluster= new JedisCluster(nodes, TIMEOUT, SO_TIMEOUT, MAX_ATTEMPTS, REDIS_AUTH, poolConfig);
        return new DefaultPooledObject<JedisCluster>(JedisCluster);
    }


    /**
      * 功能描述：钝化资源对象
      * 
      * 什么时候会调用此方法
      * 1：将资源返还给资源池时，调用此方法。
      */
    @Override
    public void passivateObject(PooledObject<JedisCluster> arg0) throws Exception {

    }


    /**
      * 功能描述：判断资源对象是否有效，有效返回 true，无效返回 false
      * 
      * 什么时候会调用此方法
      * 1：从资源池中获取资源的时候，参数 testOnBorrow 或者 testOnCreate 中有一个 配置 为 true 时，则调用  factory.validateObject() 方法
      * 2：将资源返还给资源池的时候，参数 testOnReturn，配置为 true 时，调用此方法
      * 3：资源回收线程，回收资源的时候，参数 testWhileIdle，配置为 true 时，调用此方法
      */
    @Override
    public boolean validateObject(PooledObject<JedisCluster> arg0) {

        return true;
    }
}
