package com.wulang.boot.redis.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author wulang
 * @create 2019/12/12/16:20
 */
@Data
public class LettuceSingleProperties {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String password;
}
