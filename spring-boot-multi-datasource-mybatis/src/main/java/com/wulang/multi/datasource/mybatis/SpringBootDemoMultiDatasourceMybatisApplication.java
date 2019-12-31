package com.wulang.multi.datasource.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wulang
 * @create 2019/12/31/12:11
 */
@SpringBootApplication
@MapperScan(basePackages = "com.wulang.multi.datasource.mybatis.mapper")
public class SpringBootDemoMultiDatasourceMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoMultiDatasourceMybatisApplication.class, args);
    }

}
