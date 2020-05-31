package com.wulang.multievel.spring;

import org.springframework.context.ApplicationContext;

/**
 * @author wulang
 * @create 2020/5/16/14:44
 */
public class SpringContext {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContext.applicationContext = applicationContext;
    }

}
