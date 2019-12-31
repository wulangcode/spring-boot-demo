package com.wulang.batis.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        System.out.println("设置ApplicationContext成功！");
        this.context = context;

    }

    /**
     * 获取静态变量中的ApplicationContext.
     */
    public static ApplicationContext getContext() {
        return context;
    }
}

