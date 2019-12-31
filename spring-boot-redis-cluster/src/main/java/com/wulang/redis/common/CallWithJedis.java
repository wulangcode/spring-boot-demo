package com.wulang.redis.common;

import redis.clients.jedis.Jedis;

/**
 * @author wulang
 * @create 2019/11/26/16:10
 */
public interface CallWithJedis {
    public void call(Jedis jedis);
}
