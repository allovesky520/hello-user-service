package com.springboot.demo.domin.hello.service.impl;

import com.springboot.demo.domin.hello.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class HelloServiceImpl implements HelloService {

    @Autowired
    @Qualifier(value = "ssoRedisTemplate")
    private RedisTemplate redisTemplate;

    @Value(value = "#{T(java.lang.Integer).parseInt('${sso.forgot_verify_code_effect_mins:5}')}")
    private Integer verifyCodeEffectMins;

    @Override
    public String hello(String str){
        return "Hello Spring Boot";
    }

    @Override
    public String testRedis(String str) {

        //将生成的随机码存储到Redis中
        redisTemplate.opsForValue().set(str, "abc", verifyCodeEffectMins, TimeUnit.MINUTES);
        return redisTemplate.opsForValue().get(str).toString();
    }

    public void niubi(){
        ExecutorService executorService = Executors.newFixedThreadPool(3);
    }
}
