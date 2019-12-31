package com.wulang.boot.redis.alternative.config;

import java.util.HashMap;

/**
 * 缓存Cacheable cacheNames过期时间
 *
 * @author wulang
 * @create 2019/12/16 0016/10:08
 */
public class CacheExpiresMap {
    private static HashMap<String, Integer> map = new HashMap<>();

    static {
        map.put("dic", 60);
    }


    public static HashMap<String, Integer> get() {
        return map;
    }
}
