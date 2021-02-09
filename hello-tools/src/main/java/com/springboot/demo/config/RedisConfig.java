package com.springboot.demo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.demo.properties.RedisProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

@Configuration(value = "ssoRedisConfig")
public class RedisConfig {

    @Autowired
    private RedisProperty redis;

    /**
     * 为RedisTemplate配置Redis连接工厂实现
     * LettuceConnectionFactory实现了RedisConnectionFactory接口
     *
     * @return 返回LettuceConnectionFactory
     */
    public RedisConnectionFactory ssoRedisConnectionFactory() {
        //ClientResources clientResources = DefaultClientResources.create();
        JedisConnectionFactory factory;
        if (redis.getNodes() != null) {
            final RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(redis.getNodes());
            if (StringUtils.hasText(redis.getPassword())) {
                clusterConfiguration.setPassword(RedisPassword.of(redis.getPassword()));
            }
            factory = new JedisConnectionFactory(clusterConfiguration, getJedisPoolConfig());
        } else {
            final RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration(redis.getHost(), redis.getPort());
            if (StringUtils.hasText(redis.getPassword())) {
                standaloneConfiguration.setPassword(RedisPassword.of(redis.getPassword()));
            }
            standaloneConfiguration.setDatabase(redis.getDatabase());
            factory = new JedisConnectionFactory(standaloneConfiguration);
        }
        factory.afterPropertiesSet();
        return factory;
    }

    /**
     * 配置RedisTemplate
     * 【Redis配置最终一步】
     *
     * @return 返回一个可以使用的RedisTemplate实例
     */
    @Bean(name = "ssoRedisTemplate")
    public RedisTemplate<String, Object> ssoRedisTemplate(){
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(ssoRedisConnectionFactory());
        template.setKeySerializer(jackson2JsonRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 读取Jedis连接池配置
     * @return
     */
    public JedisPoolConfig getJedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(redis.getPool().getMinIdle());
        jedisPoolConfig.setMaxIdle(redis.getPool().getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redis.getPool().getMaxWait().toMillis());
        jedisPoolConfig.setMaxTotal(redis.getPool().getMaxActive());
        //逐出连接的最小空闲时间，默认为180000毫秒（30分钟）
        jedisPoolConfig.setMinEvictableIdleTimeMillis(60000);
        //对象空闲多久后逐出，当空闲时间>该值，且 空闲连接>最大空闲连接数 时直接逐出，不再根据MinEvictableIdleTimeMillis判断
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(60000);
        //逐出扫描的时间间隔（毫秒）如果为负数，则不运行逐出线程，默认为-1
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(60000);
        return jedisPoolConfig;
    }

}
