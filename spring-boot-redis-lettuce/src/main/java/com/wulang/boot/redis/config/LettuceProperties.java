package com.wulang.boot.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 一般情况下，每个应用应该使用单个Redis客户端实例和单个连接实例，这里设计一个脚手架，
 * 适配单机、普通主从、哨兵和集群四种使用场景。对于客户端资源，采用默认的实现即可。
 * 对于Redis的连接属性，比较主要的有Host、Port和Password，其他可以暂时忽略。
 * 基于约定大于配置的原则，先定制一系列属性配置类（其实有些配置是可以完全共用，但是考虑到
 * 要清晰描述类之间的关系，这里拆分多个配置属性类和多个配置方法）
 * @author wulang
 * @create 2019/12/12/16:19
 */
@Data
@ConfigurationProperties(prefix = "lettuce")
public class LettuceProperties {
    private LettuceSingleProperties single;
    private LettuceReplicaProperties replica;
    private LettuceSentinelProperties sentinel;
    private LettuceClusterProperties cluster;
}
