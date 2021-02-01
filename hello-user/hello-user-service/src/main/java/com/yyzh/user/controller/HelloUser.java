package com.yyzh.user.controller;

import com.yyzh.user.service.HelloService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/user")
@Api(value = "HelloUser", tags = "测试", description = "我看行")
public class HelloUser {

    @Autowired
    HelloService helloUserService;

    @GetMapping(value = "/hello")
    public String hello(@RequestParam("str") String str){
        return helloUserService.hello(str);
    }



}
