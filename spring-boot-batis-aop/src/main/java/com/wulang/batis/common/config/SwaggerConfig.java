package com.wulang.batis.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket systemApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("wulang-batis-system-api")
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wulang.batis.system.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    @Bean
    public Docket adminApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("wulang-batis-admin-api")
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wulang.batis.admin.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}