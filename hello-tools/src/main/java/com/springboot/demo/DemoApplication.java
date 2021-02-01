package com.springboot.demo;

import com.springboot.demo.properties.RootProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient	//	eureka客户端
@EnableFeignClients	//	启用feign客户端
//@EnableDiscoveryClient	//	OpenFeignServer注册到eureka server
@SpringCloudApplication	//	包括：@SpringBootApplication、@EnableDiscoveryClient、@EnableCircuitBreaker
@EnableConfigurationProperties(RootProperties.class)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("hello-tools服务启动...");
	}

}
