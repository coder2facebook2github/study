package com.spring.boot.study.common;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Set;

@Configuration
public class RedisConfig {

    @Value("${redis.sentinel.master}")
    private String masterName;
    @Value("${redis.sentinel.nodes}")
    private Set<String> redisSentinelNodes;

    @Bean("jedisPoolConfig")
    @ConfigurationProperties(prefix = "redis.pool.config")
    public JedisPoolConfig getJedisPoolConfig() {
        return new JedisPoolConfig();
    }

    @Bean("jedisSentinelPool")
    public JedisSentinelPool getJedisSentinelPool() {
        JedisPoolConfig jedisPoolConfig = getJedisPoolConfig();
        return new JedisSentinelPool(masterName, redisSentinelNodes, jedisPoolConfig);
    }

    @Bean("JedisService")
    public JedisService getJedisService() {
        JedisSentinelPool jedisSentinelPool = getJedisSentinelPool();
        JedisService jedisService = new JedisService();
        jedisService.setJedisPool(jedisSentinelPool);
        return jedisService;
    }


}
