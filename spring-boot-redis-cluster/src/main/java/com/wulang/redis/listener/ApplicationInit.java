package com.wulang.redis.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * @author wulang
 * @create 2019/11/27/16:42
 */
public class ApplicationInit implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInit.class);


    /**
     * 初始化项目环境
     */
    private void initProjectEnvironment() {
        //初始化jedis池
//        JedisPoolCacheUtils.initialPool(null);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initProjectEnvironment();
    }
}
