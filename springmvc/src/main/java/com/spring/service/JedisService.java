package com.spring.service;

import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

import java.util.List;
import java.util.Map;

public interface JedisService {

    public abstract Long del(String key);

    public abstract <T extends Object> T get(String key);

    public abstract Pool<Jedis> getJedisPool();

    public abstract String getStr(String key);

    public Long hdel(final String key, final String field);

    public <T extends Object> T hget(String key, String field);

    public Map<byte[], byte[]> hgetAll(String key);

    public <T extends Object> Long hset(final String key, final String field, final T value, int seconds);

    public abstract boolean isExist(String key);

    public <T extends Object> T lindex(String key, int index);

    public <T extends Object> T lpop(String key);

    public abstract <T extends Object> void lpush(String key, T value);

    Long llen(String key);

    public <T extends Object> List<T> lrange(String key, int start, int end);

    public <T extends Object> void lset(String key, int index, T value);

    public abstract <T extends Object> void rpush(String key, T value);

    public abstract <T extends Object> void set(String key, T value);

    public abstract <T extends Object> void set(String key, T value, int seconds);

    public abstract void setJedisPool(Pool<Jedis> jedisPool);

    public void setStr(String key, String value);

    public abstract <T extends Object> Long zadd(String key, double score, T member);

    public abstract Long zcard(String key);

    public Long zcount(String key, double min, double max);

    public <T extends Object> Long zrank(String key, T member);

    public abstract <T extends Object> Long zrem(String key, T member);

    public abstract <T extends Object> List<T> zrevrange(String key, int start, int end);

    public <T extends Object> Long zrevrank(String key, T member);
}