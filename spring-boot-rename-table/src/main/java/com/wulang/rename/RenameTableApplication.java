package com.wulang.rename;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wulang
 * @create 2021/3/31/21:05
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.wulang.rename.dao"})
public class RenameTableApplication {
    public static void main(String[] args) {

        SpringApplication.run(RenameTableApplication.class, args);
    }
}
