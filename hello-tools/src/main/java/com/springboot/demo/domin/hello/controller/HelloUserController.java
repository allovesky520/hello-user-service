package com.springboot.demo.domin.hello.controller;

import com.yyzh.user.api.feign.RemoteUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hellouser")
@Api(value = "HelloUserController", tags = "测试用户", description = "我看行")
public class HelloUserController {

    @Autowired
    private RemoteUserService remoteUserService;

    @GetMapping(value = "/hello")
    public String hello(@RequestParam("str") String str){
        return remoteUserService.hello(str);
    }
}
