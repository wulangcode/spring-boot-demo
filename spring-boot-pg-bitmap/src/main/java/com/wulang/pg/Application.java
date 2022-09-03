package com.wulang.pg;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author wulang
 * @date 2022-09-03 19:20
 **/
@SpringBootApplication(exclude = MybatisPlusAutoConfiguration.class, scanBasePackages = {"com.wulang"})
@ServletComponentScan
@MapperScan(basePackages = {"com.wulang.pg"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
