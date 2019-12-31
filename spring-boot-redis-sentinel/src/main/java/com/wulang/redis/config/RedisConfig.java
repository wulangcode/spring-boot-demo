package com.wulang.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 实现一个Redis Sentinel客户端的基本步骤如下:
 * 1）遍历Sentinel节点集合获取一个可用的Sentinel节点，Sentinel节点之间可以共享数据，所以从任意一个Sentinel节点获取主节点信息都是可以的
 * 2）通过sentinel get-master-addr-by-name master-name这个API来获取对应主节点的相关信息
 * 3）验证当前获取的“主节点”是真正的主节点，这样做的目的是为了防止故障转移期间主节点的变化
 * 4）保持和Sentinel节点集合的“联系”，时刻获取关于主节点的相关“信息”
 *
 * Redis Sentinel客户端只有在初始化和切换主节点时需要和Sentinel节点集合进行交互来获取主节点信息，所以在设计客户
 * 端时需要将Sentinel节点集合考虑成配置（相关节点信息和变化）发现服务。
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig {
    private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Value("#{'${spring.redis.sentinel.nodes}'.split(',')}")
    private List<String> nodes;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.sentinel.master}")
    private String master;

    /**
     * JedisSentinelPool初始化代码的重要函数 initSentinels（Set<String>sentinels，final String masterName）
     *
     * 详情见下
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix="spring.redis")
    public JedisPoolConfig getRedisConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }
    /*
    private HostAndPort initSentinels(Set<String> sentinels, final String masterName) {
        // 主节点
        HostAndPort master = null;
        // 遍历所有sentinel节点
        for (String sentinel : sentinels) {
        // 连接sentinel节点
            HostAndPort hap = toHostAndPort(Arrays.asList(sentinel.split(":")));
            Jedis jedis = new Jedis(hap.getHost(), hap.getPort());
        // 使用sentinel get-master-addr-by-name masterName获取主节点信息
            List<String> masterAddr = jedis.sentinelGetMasterAddrByName(masterName);
        // 命令返回列表为空或者长度不为2，继续从下一个sentinel节点查询
            if (masterAddr == null || masterAddr.size() != 2) {
                continue;
            }
        // 解析masterAddr获取主节点信息
            master = toHostAndPort(masterAddr);
        // 找到后直接跳出for循环
            break;
        }
        if (master == null) {
        // 直接抛出异常，
            throw new Exception();
        }
        // 为每个sentinel节点开启主节点switch的监控线程
        for (String sentinel : sentinels) {
            final HostAndPort hap = toHostAndPort(Arrays.asList(sentinel.split(":")));
            MasterListener masterListener = new MasterListener(masterName, hap.getHost(),
                    hap.getPort());
            masterListener.start();
        }
        // 返回结果
        return master;
    }
        */

    /**
     下面代码就是MasterListener的核心监听代码，代码中比较重要的部分就是订阅Sentinel节点的+switch-master频道，它就是Redis Sentinel在结束对主
     节点故障转移后会发布切换主节点的消息，Sentinel节点基本将故障转移的各个阶段发生的行为都通过这种发布订阅的形式对外提供，开发者只需订阅
     感兴趣的频道即可,这里我们比较关心的是+switch-master这个频道。
     */
    /*
    Jedis sentinelJedis = new Jedis(sentinelHost, sentinelPort);
        // 客户端订阅Sentinel节点上"+switch-master"(切换主节点)频道
        sentinelJedis.subscribe(new JedisPubSub() {
        @Override
        public void onMessage(String channel, String message) {
            String[] switchMasterMsg = message.split(" ");
            if (switchMasterMsg.length > 3) {
            // 判断是否为当前masterName
                if (masterName.equals(switchMasterMsg[0])) {
                // 发现当前masterName发生switch，使用initPool重新初始化连接池
                    initPool(toHostAndPort(switchMasterMsg[3], switchMasterMsg[4]));
                }
            }
        }
    }, "+switch-master");
    */

    /**
     * public JedisSentinelPool(String masterName, Set<String> sentinels,final GenericObjectPoolConfig poolConfig, final int connectionTimeout,
     * final int soTimeout,final String password, final int database,final String clientName){
     *     …
     *      HostAndPort master = initSentinels(sentinels, masterName);
     *      initPool(master);
     *     …
     * }
     *
     * ·masterName——主节点名。
     * ·sentinels——Sentinel节点集合。
     * ·poolConfig——common-pool连接池配置。
     * ·connectTimeout——连接超时。
     * ·soTimeout——读写超时。
     * ·password——主节点密码。
     * ·database——当前数据库索引。
     * ·clientName——客户端名。
     *
     *
     * 伪代码逻辑：
     *      Jedis jedis = null;
     *      try {
     *              jedis = jedisSentinelPool.getResource();
     *              // jedis command
     *          } catch (Exception e) {
     *              logger.error(e.getMessage(), e);
     *             } finally {
     *                  if (jedis != null)
     *                      jedis.close();
     *                  }
     *
     *
     *
     */
    @Bean
    public RedisSentinelConfiguration sentinelConfiguration(){
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        //配置matser的名称
        redisSentinelConfiguration.master(master);
        //配置redis的哨兵sentinel
        Set<RedisNode> redisNodeSet = new HashSet<>();
        nodes.forEach(x->{
            redisNodeSet.add(new RedisNode(x.split(":")[0],Integer.parseInt(x.split(":")[1])));
        });
        logger.info("redisNodeSet -->"+redisNodeSet);
        redisSentinelConfiguration.setSentinels(redisNodeSet);
//        redisSentinelConfiguration.setPassword(password);
        return redisSentinelConfiguration;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig,RedisSentinelConfiguration sentinelConfig) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(sentinelConfig,jedisPoolConfig);
        return jedisConnectionFactory;
    }



}
