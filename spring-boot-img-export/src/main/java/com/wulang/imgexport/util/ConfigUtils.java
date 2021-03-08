package com.wulang.imgexport.util;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author wulang
 * @create 2021/3/7/21:41
 */
@Component
public class ConfigUtils implements EnvironmentAware {
    private static Environment env;

    // 项目环境
    public static final String ENVIRONMENT = System.getProperty("ENVIRONMENT") != null
        ? System.getProperty("ENVIRONMENT")
        : "dev";

    public static String getProperty(String key) {
        return env.getProperty(key);
    }

    @Override
    public void setEnvironment(Environment environment) {
        env = environment;
    }
}
