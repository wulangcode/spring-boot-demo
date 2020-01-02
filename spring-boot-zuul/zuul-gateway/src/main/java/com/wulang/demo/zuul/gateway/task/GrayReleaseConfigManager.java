package com.wulang.demo.zuul.gateway.task;

import com.wulang.demo.zuul.gateway.model.GrayReleaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wulang
 * @create 2020/1/1/9:49
 */
@Component
@Configuration
public class GrayReleaseConfigManager {

    private Map<String, GrayReleaseConfig> grayReleaseConfigs =
            new ConcurrentHashMap<String, GrayReleaseConfig>();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Scheduled(fixedRate = 1000)
    private void refreshRoute() {
        List<GrayReleaseConfig> results = jdbcTemplate.query(
                "select * from gray_release_config",
                new BeanPropertyRowMapper<>(GrayReleaseConfig.class));

        for(GrayReleaseConfig grayReleaseConfig : results) {
            grayReleaseConfigs.put(grayReleaseConfig.getPath(), grayReleaseConfig);
        }
    }

    public Map<String, GrayReleaseConfig> getGrayReleaseConfigs() {
        return grayReleaseConfigs;
    }

}
