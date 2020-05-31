package com.wulang.multievel.listener;

import com.wulang.multievel.kafka.KafkaConsumer;
import com.wulang.multievel.rebuild.RebuildCacheThread;
import com.wulang.multievel.spring.SpringContext;
import com.wulang.multievel.zk.ZooKeeperSession;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 系统初始化的监听器
 *
 * @author wulang
 * @create 2020/5/16/14:43
 */
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc);
        SpringContext.setApplicationContext(context);

        new Thread(new KafkaConsumer("cache-message")).start();
        new Thread(new RebuildCacheThread()).start();

        ZooKeeperSession.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
