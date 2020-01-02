package com.wulang.demo.zuul.gateway.config;

import com.wulang.demo.zuul.gateway.model.GatewayApiRoute;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现动态路由
 *
 * SimpleRouteLocator是zuul基础路由加载类，初始化的时候自动将配置文件中配置的路由规则加载到内存中，重写locateRoutes方法将路由规则配置改为自定义的源。
 * RefreshableRouteLocator接口仅提供刷新路由表的方法，实现很简单。
 * @author wulang
 * @create 2020/1/1/9:35
 */
public class DynamicRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    private JdbcTemplate jdbcTemplate;
    private ZuulProperties properties;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DynamicRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();
        // 加载application.yml中的路由表
        routesMap.putAll(super.locateRoutes());
        // 加载db中的路由表
        routesMap.putAll(locateRoutesFromDB());

        // 统一处理一下路由path的格式
        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }

        System.out.println("路由表：" + values);

        return values;
    }

    private Map<String, ZuulProperties.ZuulRoute> locateRoutesFromDB() {
        Map<String, ZuulProperties.ZuulRoute> routes = new LinkedHashMap<>();

        List<GatewayApiRoute> results = jdbcTemplate.query(
                "select * from gateway_api_route where enabled = true ",
                new BeanPropertyRowMapper<>(GatewayApiRoute.class));

        for (GatewayApiRoute result : results) {
            if (StringUtils.isEmpty(result.getPath()) ) {
                continue;
            }
            if (StringUtils.isEmpty(result.getServiceId()) && StringUtils.isEmpty(result.getUrl())) {
                continue;
            }
            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
            try {
                BeanUtils.copyProperties(result, zuulRoute);
            } catch (Exception e) {
                e.printStackTrace();
            }
            routes.put(zuulRoute.getPath(), zuulRoute);
        }

        return routes;
    }

}
