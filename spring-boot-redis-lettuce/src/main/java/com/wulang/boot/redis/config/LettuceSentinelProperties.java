package com.wulang.boot.redis.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LettuceSentinelProperties extends LettuceSingleProperties {

    private String masterId;
}