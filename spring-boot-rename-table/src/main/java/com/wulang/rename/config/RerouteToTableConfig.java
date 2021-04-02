package com.wulang.rename.config;

import com.wulang.rename.util.TableNameFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wulang
 * @create 2021/3/31/21:34
 */
@Configuration
public class RerouteToTableConfig {

    @Bean
    public RerouteToTableInterceptor autoIdInterceptor() {
        RerouteToTableInterceptor rerouteToTableInterceptor = new RerouteToTableInterceptor();
        rerouteToTableInterceptor.setMap(TableNameFactory.getMap());
        return rerouteToTableInterceptor;
    }
}
