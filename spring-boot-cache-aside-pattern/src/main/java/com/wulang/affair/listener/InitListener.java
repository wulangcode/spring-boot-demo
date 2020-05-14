package com.wulang.affair.listener;

import com.wulang.affair.thread.RequestProcessorThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 系统初始化监听器
 *
 * @author wulang
 * @create 2020/5/13/7:36
 */
public class InitListener implements ServletContextListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitListener.class);


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("===========日志===========: 线程池初始化开始");
        // 初始化工作线程池和内存队列
        RequestProcessorThreadPool.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
