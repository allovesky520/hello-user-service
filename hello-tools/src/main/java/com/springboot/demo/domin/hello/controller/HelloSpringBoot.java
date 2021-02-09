package com.springboot.demo.domin.hello.controller;

import com.springboot.demo.annotation.sysLog.aspect.SysLog;
import com.springboot.demo.domin.hello.service.HelloService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/test")
@Api(value = "HelloSpringBoot", tags = "测试", description = "我看行")
public class HelloSpringBoot {

    @Autowired
    HelloService helloService;

    @SysLog("hello 测试")
    @GetMapping(value = "/hello")
    public String hello(@RequestParam("str") String str){
        return helloService.hello(str);
    }

    @SysLog("redis 测试")
    @GetMapping(value = "/redis")
    public String testRedis(@RequestParam("str") String str){
        return helloService.testRedis(str);
    }



}
