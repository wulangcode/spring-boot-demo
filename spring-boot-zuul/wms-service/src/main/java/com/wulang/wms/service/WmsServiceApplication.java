package com.wulang.wms.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WmsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WmsServiceApplication.class, args);
	}
	
}
