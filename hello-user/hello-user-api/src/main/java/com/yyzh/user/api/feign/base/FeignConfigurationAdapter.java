package com.yyzh.user.api.feign.base;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * feign的 通用配置
 * @author zihui
 * @date 2021-01-25 11:20:29
 **/
@Configuration
public class FeignConfigurationAdapter {

    @Bean
    Logger.Level feignLoggerLevel() {
        //设置日志等级
        return Logger.Level.FULL;
    }

}
