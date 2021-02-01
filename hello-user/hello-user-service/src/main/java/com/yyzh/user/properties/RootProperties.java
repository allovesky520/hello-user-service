package com.yyzh.user.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zihui
 * @version 1.0
 * @title: RootProperties
 * @description: TODO
 * @date 2021-01-14 10:37:21
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "springboot.demo")
public class RootProperties {
    /**
     * swagger配置
     */
    private SwaggerProperties swagger=new SwaggerProperties();
}
