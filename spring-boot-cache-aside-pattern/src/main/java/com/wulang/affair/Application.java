package com.wulang.affair;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import com.wulang.affair.filter.HystrixRequestContextFilter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wulang
 * @create 2020/5/12/20:42
 */
@SpringBootApplication
@MapperScan("com.wulang.affair.mapper")
public class Application {
    //连接超时时间
    public static final int TIMEOUT = 3000;
    //间隔超时时间
    public static final int SO_TIMEOUT = 100;
    //重试次数
    public static final int MAX_ATTEMPTS = 100;
    //Redis数据库访问密码
    public static final String REDIS_AUTH = "123456";
    //最大连接数
    public static final int MAX_TOTAL = 1000;
    // 空闲连接数
    public static final int MAX_IDEL = 200;
    //最大等待时间
    public static final int MAX_WAIT_MILLIS = 1000;
    // 进行连接测试，以保证返回的连接为可用连接
    public static final boolean TEST_ON_BORROW = true;

    /**
     * 构建数据源
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DataSource();
    }

    /**
     * 构建MyBatis的入口类：SqlSessionFactory
     *
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 构建事务管理器
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public JedisCluster JedisClusterFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        jedisClusterNodes.add(new HostAndPort("122.152.197.167", 6379));
        jedisClusterNodes.add(new HostAndPort("122.152.197.167", 6380));
        jedisClusterNodes.add(new HostAndPort("122.152.197.167", 6381));
        jedisClusterNodes.add(new HostAndPort("122.152.197.167", 6382));
        jedisClusterNodes.add(new HostAndPort("122.152.197.167", 6383));
        jedisClusterNodes.add(new HostAndPort("122.152.197.167", 6384));
        // 最大连接数
        poolConfig.setMaxTotal(MAX_TOTAL);
        // 空闲连接数(最小维持连接数)
        poolConfig.setMaxIdle(MAX_IDEL);
        // 最大等待时间
        poolConfig.setMaxWaitMillis(MAX_WAIT_MILLIS);
        poolConfig.setTestOnBorrow(TEST_ON_BORROW);
        //创建RedisCluster集群访问对象
        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, TIMEOUT, SO_TIMEOUT, MAX_ATTEMPTS, REDIS_AUTH, poolConfig);
        return jedisCluster;
    }

//    /**
//     * 注册监听器
//     *
//     * @return
//     */
//    @Bean
//    public ServletListenerRegistrationBean servletListenerRegistrationBean() {
//        ServletListenerRegistrationBean servletListenerRegistrationBean =
//            new ServletListenerRegistrationBean();
//        servletListenerRegistrationBean.setListener(new InitListener());
//        return servletListenerRegistrationBean;
//    }

    @Bean
    public ServletRegistrationBean indexServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new HystrixMetricsStreamServlet());
        registration.addUrlMappings("/hystrix.stream");
        return registration;
    }

    /**
     * HystrixRequestContext
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(
            new HystrixRequestContextFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
