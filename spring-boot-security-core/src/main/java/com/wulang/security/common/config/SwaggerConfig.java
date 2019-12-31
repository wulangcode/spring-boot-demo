package com.wulang.security.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: wulang
 * @date: 2019/11/19
 * @description:
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket systemApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("whlg-security-system-api")
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wulang.security.system.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    @Bean
    public Docket adminApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("whlg-security-admin-api")
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wulang.security.admin.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    @Bean
    public Docket adminApi1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("whlg-security-admin-api1")
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wulang.security.serialPort.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}