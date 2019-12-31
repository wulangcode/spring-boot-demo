package com.wulang.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableAsync
@EnableScheduling
@EnableWebSecurity
@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args){
        SpringApplication.run(SecurityApplication.class, args);
    }
}
