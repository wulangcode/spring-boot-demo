package com.wulang.pg.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.Data;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author wulang
 * @date 2022-09-03 19:23
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "pg.jdbc.datasource")
@MapperScan(basePackages = {"com.wulang.bitmap.mapper.mapper"}, sqlSessionFactoryRef = "pgSqlSessionFactoryBean")
public class PgDataSourceConfig {

    @Value("${pg.jdbc.datasource.username}")
    private String username;

    @Value("${pg.jdbc.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${pg.jdbc.datasource.password}")
    private String password;

    @Value("${pg.jdbc.datasource.url}")
    private String url;

    @Value("${pg.jdbc.datasource.type}")
    private String type;

    @Value("${pg.jdbc.datasource.max-wait:10000}")
    private long maxWait;
    /**
     * 最小连接池数量
     */
    @Value("${pg.jdbc.datasource.min-idle:2}")
    private int minIdle;
    /**
     * 最大连接数量
     */
    @Value("${pg.jdbc.datasource.max-active:20}")
    private int maxActive;

    @Bean(name = "pgDataSource")
    public DataSource pgDataSource() throws Exception {
        Class classes = Class.forName(type);
        DruidDataSource dataSource = (DruidDataSource) DataSourceBuilder
            .create()
            .driverClassName(driverClassName)
            .type(classes)
            .url(url)
            .username(username)
            .password(password)
            .build();
        dataSource.setMaxWait(maxWait);
        dataSource.setMinIdle(minIdle);
        dataSource.setValidationQueryTimeout(500);
        dataSource.setMaxActive(maxActive);
        // 设置druid 连接池非公平锁模式
        dataSource.setUseUnfairLock(true);
        return dataSource;
    }

    @Bean(name = "pgSqlSessionFactoryBean")
    public SqlSessionFactory pgSqlSessionFactoryBean(@Qualifier("pgDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeHandlers(new BitMapTypeHandler());
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factory.setMapperLocations(resolver.getResources("classpath:mapper/pg/*.xml"));
        //开启驼峰命名转换
        factory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return factory.getObject();
    }

}
