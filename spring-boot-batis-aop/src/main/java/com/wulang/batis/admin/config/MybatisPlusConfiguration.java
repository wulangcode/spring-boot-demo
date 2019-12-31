package com.wulang.batis.admin.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wulang
 * @create 2019/12/10/17:38
 */
@Configuration
@MapperScan(basePackages = "com.wulang.batis.**.**.dao")
public class MybatisPlusConfiguration {

    @Value("${mybatis-plus.mapper-locations}")
    private String mapperLocations;

    /*@Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 开启 PageHelper 的支持
        paginationInterceptor.setLocalPage(true);
        return paginationInterceptor;
    }*/

    /**
     * SQL执行效率插件
     */
    @Bean
    @Profile({"dev", "pro", "test"})// 设置 dev prod 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setMaxTime(1000);
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }

    /**
     *
     * @return
     */
//    @Bean(name = "collection")
    @Bean(name = "db1")
//    @ConfigurationProperties(prefix = "spring.datasource.collection")
    @ConfigurationProperties(prefix = "spring.datasource.druid.db1")
    public DataSource collection() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     *
     * @return
     */
//    @Bean(name = "monitor")
    @Bean(name = "db2")
//    @ConfigurationProperties(prefix = "spring.datasource.monitor")
    @ConfigurationProperties(prefix = "spring.datasource.druid.db2")
    public DataSource monitor() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 动态数据源配置
     *
     * 若有更多数据源，根据自己实际情况进行配置
     *
     * @return
     */
    @Bean
    @Primary
    public DataSource multipleDataSource(@Qualifier("db1") DataSource db1,
                                         @Qualifier("db2") DataSource db2) {
        MultipleDataSource multipleDataSource = new MultipleDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceEnum.DB1.getValue(), db1);
        targetDataSources.put(DataSourceEnum.DB2.getValue(), db2);
        //添加数据源
        multipleDataSource.setTargetDataSources(targetDataSources);
        //设置默认数据源
        multipleDataSource.setDefaultTargetDataSource(db1);

        return multipleDataSource;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(multipleDataSource(collection(), monitor()));
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        sqlSessionFactory.setConfiguration(configuration);
        return sqlSessionFactory.getObject();
    }
}
