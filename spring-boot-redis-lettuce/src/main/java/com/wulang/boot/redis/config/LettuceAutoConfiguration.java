package com.wulang.boot.redis.config;

/**
 * 主要使用@ConditionalOnProperty做隔离，一般情况下，很少有人会在一个应用使用一种以上的Redis连接场景
 *
 *
 * @ConditionalOnProperty：
 * 这个注解能够控制某个configuration是否生效。
 * 具体操作是通过其两个属性name以及havingValue来实现的，其中name用来从application.properties中读取某个属性值，如
 * 果该值为空，则返回false;如果值不为空，则将该值与havingValue指定的值进行比较，如果一样则返回true;否则返回false。
 * 如果返回值为false，则该configuration不生效；为true则生效。
 *
 *
 * @ConditionalOnClass：
 * 会检查类加载器中是否存在对应的类，如果有的话被注解修饰的类就有资格被Spring容器所注册，否则会被skip。
 *
 *
 * @EnableConfigurationProperties：
 * 使使用@ConfigurationProperties 注解的类生效。
 *
 * @author wulang
 * @create 2019/12/12/16:23
 */

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.codec.Utf8StringCodec;
import io.lettuce.core.masterslave.MasterSlave;
import io.lettuce.core.masterslave.StatefulRedisMasterSlaveConnection;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@ConditionalOnClass(name = "io.lettuce.core.RedisURI")
@EnableConfigurationProperties(value = LettuceProperties.class)
public class LettuceAutoConfiguration {
    private final LettuceProperties lettuceProperties;

    @Bean(destroyMethod = "shutdown")
    public ClientResources clientResources() {
        return DefaultClientResources.create();
    }

    @Bean
    @ConditionalOnProperty(name = "lettuce.single.host")
    public RedisURI singleRedisUri() {
        LettuceSingleProperties singleProperties = lettuceProperties.getSingle();
        return RedisURI.builder()
                .withHost(singleProperties.getHost())
                .withPort(singleProperties.getPort())
                .withPassword(singleProperties.getPassword())
                .build();
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "lettuce.single.host")
    public RedisClient singleRedisClient(ClientResources clientResources, @Qualifier("singleRedisUri") RedisURI redisUri) {
        return RedisClient.create(clientResources, redisUri);
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnProperty(name = "lettuce.single.host")
    public StatefulRedisConnection<String, String> singleRedisConnection(@Qualifier("singleRedisClient") RedisClient singleRedisClient) {
        return singleRedisClient.connect();
    }

    @Bean
    @ConditionalOnProperty(name = "lettuce.replica.host")
    public RedisURI replicaRedisUri() {
        LettuceReplicaProperties replicaProperties = lettuceProperties.getReplica();
        return RedisURI.builder()
                .withHost(replicaProperties.getHost())
                .withPort(replicaProperties.getPort())
                .withPassword(replicaProperties.getPassword())
                .build();
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "lettuce.replica.host")
    public RedisClient replicaRedisClient(ClientResources clientResources, @Qualifier("replicaRedisUri") RedisURI redisUri) {
        return RedisClient.create(clientResources, redisUri);
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnProperty(name = "lettuce.replica.host")
    public StatefulRedisMasterSlaveConnection<String, String> replicaRedisConnection(@Qualifier("replicaRedisClient") RedisClient replicaRedisClient,
                                                                                     @Qualifier("replicaRedisUri") RedisURI redisUri) {
        return MasterSlave.connect(replicaRedisClient, new Utf8StringCodec(), redisUri);
    }

    @Bean
    @ConditionalOnProperty(name = "lettuce.sentinel.host")
    public RedisURI sentinelRedisUri() {
        LettuceSentinelProperties sentinelProperties = lettuceProperties.getSentinel();
        return RedisURI.builder()
                .withPassword(sentinelProperties.getPassword())
                .withSentinel(sentinelProperties.getHost(), sentinelProperties.getPort())
                .withSentinelMasterId(sentinelProperties.getMasterId())
                .build();
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "lettuce.sentinel.host")
    public RedisClient sentinelRedisClient(ClientResources clientResources, @Qualifier("sentinelRedisUri") RedisURI redisUri) {
        return RedisClient.create(clientResources, redisUri);
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnProperty(name = "lettuce.sentinel.host")
    public StatefulRedisMasterSlaveConnection<String, String> sentinelRedisConnection(@Qualifier("sentinelRedisClient") RedisClient sentinelRedisClient,
                                                                                      @Qualifier("sentinelRedisUri") RedisURI redisUri) {
        return MasterSlave.connect(sentinelRedisClient, new Utf8StringCodec(), redisUri);
    }

    @Bean
    @ConditionalOnProperty(name = "lettuce.cluster.host")
    public RedisURI clusterRedisUri() {
        LettuceClusterProperties clusterProperties = lettuceProperties.getCluster();
        return RedisURI.builder()
                .withHost(clusterProperties.getHost())
                .withPort(clusterProperties.getPort())
                .withPassword(clusterProperties.getPassword())
                .build();
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "lettuce.cluster.host")
    public RedisClusterClient redisClusterClient(ClientResources clientResources, @Qualifier("clusterRedisUri") RedisURI redisUri) {
        return RedisClusterClient.create(clientResources, redisUri);
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnProperty(name = "lettuce.cluster")
    public StatefulRedisClusterConnection<String, String> clusterConnection(RedisClusterClient clusterClient) {
        return clusterClient.connect();
    }
}
