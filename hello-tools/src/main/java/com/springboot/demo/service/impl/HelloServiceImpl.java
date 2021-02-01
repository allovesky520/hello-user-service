package com.springboot.demo.service.impl;

import com.springboot.demo.service.HelloService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String str){
        return "Hello Spring Boot";
    }

    public void niubi(){
        ExecutorService executorService = Executors.newFixedThreadPool(3);
    }
}
