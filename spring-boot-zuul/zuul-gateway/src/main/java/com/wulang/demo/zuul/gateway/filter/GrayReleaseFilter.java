package com.wulang.demo.zuul.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.wulang.demo.zuul.gateway.model.GrayReleaseConfig;
import com.wulang.demo.zuul.gateway.task.GrayReleaseConfigManager;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 放置这个自定义路由过滤器的位置很关键，如果你有前置处理请求参数或者用户信息鉴权等filter的话，记得把这个filter放置在他们后面配置filterOrder的值即可。
 *
 * @author wulang
 * @create 2020/1/1/9:50
 */
@Configuration
public class GrayReleaseFilter extends ZuulFilter {

    @Resource
    private GrayReleaseConfigManager grayReleaseConfigManager;

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestURI = request.getRequestURI();

        // http://localhost:9000/order/order?xxxx

        //http://localhost:9000/order/order/create?productId=1&userId=1&count=2&totalPrice=50&gray=true
        Map<String, GrayReleaseConfig> grayReleaseConfigs =
            grayReleaseConfigManager.getGrayReleaseConfigs();
        for(String path : grayReleaseConfigs.keySet()) {
            if(requestURI.contains(path)) {
                GrayReleaseConfig grayReleaseConfig = grayReleaseConfigs.get(path);
                if(grayReleaseConfig.getEnableGrayRelease() == 1) {
                    System.out.println("启用灰度发布功能");
                    return true;
                }
            }
        }

        System.out.println("不启用灰度发布功能");

        return false;
    }

    @Override
    public Object run() {
//		Random random = new Random();
//		int seed = random.nextInt() * 100;
//
//        if (seed == 50) {
//            RibbonFilterContextHolder.getCurrentContext().add("version", "new");
//        }  else {
//            RibbonFilterContextHolder.getCurrentContext().add("version", "current");
//        }

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String gray = request.getParameter("gray");

        if("true".equals(gray)) {
            RibbonFilterContextHolder.getCurrentContext().add("version", "new");
        } else {
            RibbonFilterContextHolder.getCurrentContext().add("version", "current");
        }

        return null;
    }
}
