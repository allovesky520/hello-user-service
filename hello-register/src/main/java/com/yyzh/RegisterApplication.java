package com.yyzh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 启动一个服务注册中心
 */
@EnableEurekaServer
@SpringBootApplication
public class RegisterApplication {
    public static void main(String[] args) {
        SpringApplication.run(RegisterApplication.class, args);
        System.out.println("register 注册中心服务启动...");
    }
}
