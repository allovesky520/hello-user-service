package com.yyzh.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DefaultHystrixController {

    @RequestMapping("/defaultfallback")
    public String defaultfallback(){

        log.info("服务降级中");
        return "服务异常";
    }
}