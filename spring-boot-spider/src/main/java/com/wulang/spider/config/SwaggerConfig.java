package com.wulang.spider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wulang
 * @create 2020/5/2/9:26
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket systemApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("whlg-security-system-api")
            .pathMapping("/")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.wulang.spider.controller"))
            .paths(PathSelectors.any())
            .build();
    }
    @Bean
    public Docket adminApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("whlg-security-admin-api")
            .pathMapping("/")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.wulang.spider.controller"))
            .paths(PathSelectors.any())
            .build();
    }
    @Bean
    public Docket adminApi1() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("whlg-security-admin-api1")
            .pathMapping("/")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.wulang.spider.controller"))
            .paths(PathSelectors.any())
            .build();
    }
}
