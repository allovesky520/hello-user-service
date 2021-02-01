package com.springboot.demo.controller;

import com.springboot.demo.annotation.sysLog.aspect.SysLog;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/config")
@RefreshScope   //  这边的@RefreshScope注解不能少，否则即使调用/refresh，配置也不会刷新
@Api(value = "HelloConfig", tags = "测试配置中心", description = "测试配置中心")
public class HelloConfig {
    @Value("${hello}")
    private String hello;

    @SysLog("hello config")
    @GetMapping(value = "/hello")
    public String hello(@RequestParam("str") String str){
        return hello;
    }

}
