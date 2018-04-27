package com.spring.boot.study.common.configuration;


import com.utils.JedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "redis.sentinel")
public class RedisConfig {

    private String masterName;
    private String nodes;

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    @Bean("jedisPoolConfig")
    @ConfigurationProperties(prefix = "redis.pool.config")
    public JedisPoolConfig getJedisPoolConfig() {
        return new JedisPoolConfig();
    }

    @Bean("jedisSentinelPool")
    public JedisSentinelPool getJedisSentinelPool(JedisPoolConfig jedisPoolConfig) {
        Set<String> sentinelNodes= new HashSet<>(Arrays.asList(this.nodes.split(",")));
        return new JedisSentinelPool(masterName, sentinelNodes, jedisPoolConfig);
    }

    @Bean("jedisService")
    public JedisService getJedisService(JedisSentinelPool jedisSentinelPool) {
        JedisService jedisService = new JedisService();
        jedisService.setJedisPool(jedisSentinelPool);
        return jedisService;
    }

}
