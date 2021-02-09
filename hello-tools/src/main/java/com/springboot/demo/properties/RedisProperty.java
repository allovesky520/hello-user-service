package com.springboot.demo.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Setter
@Getter
@ToString
@Component
@ConfigurationProperties(prefix = "sso.redis")  //  通过注解读取sso.redis开头的配置文件
public class RedisProperty {
    private Integer database;

    @Value("${sso.redis.cluster.nodes:#{null}}")
    private List<String> nodes;

    private String host;

    private Integer port;

    private String password;

    private Pool pool = new Pool();

    /**
     * Pool properties.
     */
    @Data
    public static class Pool {

        /**
         * Maximum number of "idle" connections in the pool. Use a negative value to
         * indicate an unlimited number of idle connections.
         */
        private int maxIdle = 50;

        /**
         * Target for the minimum number of idle connections to maintain in the pool. This
         * setting only has an effect if it is positive.
         */
        private int minIdle = 10;

        /**
         * Maximum number of connections that can be allocated by the pool at a given
         * time. Use a negative value for no limit.
         */
        private int maxActive = 200;

        /**
         * Maximum amount of time a connection allocation should block before throwing an
         * exception when the pool is exhausted. Use a negative value to block
         * indefinitely.
         */
        private Duration maxWait = Duration.ofMillis(3000);

        private int timeout = 5000;
    }
}
