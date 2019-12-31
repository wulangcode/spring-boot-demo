package com.wulang.redis.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author wulang
 * @create 2019/11/26/15:43
 */
public class ApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

//    private static Logger log = LoggerFactory.getLogger(ApplicationListener.class);
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        initProjectEnvironment();
//    }
//
//    /**
//     * 初始化项目环境
//     */
//    private void initProjectEnvironment() {
//        //初始化jedis池
//        JedisPoolCacheUtils.initialPool(null);
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent sce) { }

}
