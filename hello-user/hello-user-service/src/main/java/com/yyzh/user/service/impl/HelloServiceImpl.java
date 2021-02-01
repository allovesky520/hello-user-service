package com.yyzh.user.service.impl;

import com.yyzh.user.service.HelloService;
import org.springframework.stereotype.Service;


@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String str){
        return "Hello" + str;
    }

}
