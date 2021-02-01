package com.springboot.demo.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zihui
 * @version 1.0
 * @title: FeighConfigurer
 * @description: TODO
 * @date 2021-01-21 15:36:25
 */
@Configuration
@EnableFeignClients(basePackages = {
        "com.yyzh.user.api.feign"
})
@ComponentScan(basePackages = {
        "com.yyzh.user.api.feign.fallback"
} )
public class FeighConfig {


}
