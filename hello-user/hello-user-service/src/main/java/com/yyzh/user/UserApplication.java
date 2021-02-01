package com.yyzh.user;

import com.yyzh.user.properties.RootProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient    //	eureka客户端
//@EnableDiscoveryClient    //	OpenFeignServer注册到eureka server
@SpringCloudApplication    //	包括：@SpringBootApplication、@EnableDiscoveryClient、@EnableCircuitBreaker
@EnableConfigurationProperties(RootProperties.class)
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
		System.out.println("hello-user服务启动...");
	}

}
