package com.wulang.boot.redis.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author: fei.yao
 * @date: 2018/7/18
 * @modified by:
 */
@Component
public class SpringCacheResolver implements CacheResolver {

    @Autowired
    private CacheManager cacheManager;


    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
        List<Cache> caches = new ArrayList<>();
        Set<String> cacheNameList = context.getOperation().getCacheNames();
        if (!CollectionUtils.isEmpty(cacheNameList)) {
            for (String cacheName : context.getOperation().getCacheNames()) {
                caches.add(cacheManager.getCache(cacheName));
            }
        }
        caches.add(cacheManager.getCache("mybatis:data:cache:"+context.getTarget().getClass().getName()));
        return caches;
    }
}
