package com.wulang.demo.zuul.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *@author wulang
 * @create 2020/1/1/9:37
 */
@Configuration
public class DynamicRouteConfiguration {

    @Autowired
    private ZuulProperties zuulProperties;
    @Autowired
    private ServerProperties server;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public DynamicRouteLocator routeLocator(ZuulProperties zuulProperties) {
        /**
         * spring boot 2.0.x 方法这样获取不到，只能1.5.x 方法才能这样获得
         */
//        DynamicRouteLocator routeLocator = new DynamicRouteLocator(
//                this.server.getServletPrefix(), this.zuulProperties);
        DynamicRouteLocator routeLocator = new DynamicRouteLocator(zuulProperties.getPrefix(),this.zuulProperties);
        routeLocator.setJdbcTemplate(jdbcTemplate);
        return routeLocator;
    }

}
