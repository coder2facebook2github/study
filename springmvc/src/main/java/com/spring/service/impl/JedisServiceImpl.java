package com.spring.service.impl;

import com.spring.service.JedisService;
import com.utils.ByteArrayHelper;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisServiceImpl implements JedisService {
//    private Log logger = LogFactory.getLog(JedisServiceImpl.class);

    protected Pool<Jedis> jedisPool;

    @Override
    public Long del(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            return jedis.del(key);
        } catch (Exception e) {
            return null;
        } finally {
            if (jedis != null) {
                getJedisPool().returnResource(jedis);
            }
        }
    }

    @Override
    public <T extends Object> T get(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            return (T) ByteArrayHelper.byte2Object(jedis.get(key.getBytes()));
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
            return null;
        } finally {
            if (jedis != null) {
                getJedisPool().returnResource(jedis);
            }
        }
    }

    @Override
    public Pool<Jedis> getJedisPool() {
        return jedisPool;
    }

    @Override
    public String getStr(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            return jedis.get(key);
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
            return null;
        } finally {
            if (jedis != null) {
                getJedisPool().returnResource(jedis);
            }
        }
    }

    @Override
    public Long hdel(String key, String field) {
        Jedis jedis = getJedisPool().getResource();
        try {
            return jedis.hdel(key, field);
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
            return -1l;
        } finally {
            getJedisPool().returnResource(jedis);
        }
    }

    @Override
    public <T extends Object> T hget(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            return (T)ByteArrayHelper.byte2Object(jedis.hget(key.getBytes(), field.getBytes()));
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
            return null;
        } finally {
            if (jedis != null) {
                getJedisPool().returnResource(jedis);
            }
        }
    }

    @Override
    public Map<byte[], byte[]> hgetAll(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            return jedis.hgetAll(key.getBytes());
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
            return null;
        } finally {
            if (jedis != null) {
                getJedisPool().returnResource(jedis);
            }
        }
    }

    @Override
    public <T extends Object> Long hset(String key, String field, T value, int seconds) {

        Jedis jedis = getJedisPool().getResource();
        try {
            Long ret = jedis.hset(key.getBytes(), field.getBytes(), ByteArrayHelper.object2Bytes(value));
            if (seconds > 0) {
                jedis.expire(key, seconds);
            }
            return ret;
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
            return -1l;
        } finally {
            getJedisPool().returnResource(jedis);
        }
    }

    @Override
    public boolean isExist(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            return jedis.exists(key);
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
            return false;
        } finally {
            getJedisPool().returnResource(jedis);
        }
    }

    @Override
    public <T extends Object> T lindex(String key, int index) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            return (T)ByteArrayHelper.byte2Object(jedis.lindex(key.getBytes(), index));
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
            return null;
        } finally {
            if (jedis != null) {
                getJedisPool().returnResource(jedis);
            }
        }
    }

    @Override
    public <T extends Object> T lpop(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            T object = (T)ByteArrayHelper.byte2Object(jedis.lpop(key.getBytes()));
            return object;
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
            return null;
        } finally {
            if (jedis != null) {
                getJedisPool().returnResource(jedis);
            }
        }
    }

    @Override
    public <T extends Object> void lpush(String key, T value) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            jedis.lpush(key.getBytes(), ByteArrayHelper.object2Bytes(value));
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
        } finally {
            if (jedis != null) {
                getJedisPool().returnResource(jedis);
            }
        }
    }

    public Long llen(String key){
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            return jedis.llen(key.getBytes());
        } catch (Exception e){

        } finally {
            if(jedis != null) {
                getJedisPool().returnResource(jedis);
            }
        }
        return 0L;
    }

    @Override
    public <T extends Object> List<T> lrange(String key, int start, int end) {
        Jedis jedis = getJedisPool().getResource();
        List<T> ret = new ArrayList<T>();
        try {
            List<byte[]> list = jedis.lrange(key.getBytes(), start, end);
            for (byte[] i : list) {
                ret.add((T)ByteArrayHelper.byte2Object(i));
            }
            return ret;
        } catch (Exception e) {
//            lo4gger.error(e.getLocalizedMessage());
            return null;
        } finally {
            if (jedis != null) {
                getJedisPool().returnResource(jedis);
            }
        }
    }

    @Override
    public <T extends Object> void lset(String key, int index, T value) {
        Jedis jedis = getJedisPool().getResource();
        try {
            jedis.lset(key.getBytes(), index, ByteArrayHelper.object2Bytes(value));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                getJedisPool().returnResource(jedis);
            }
        }
    }

    @Override
    public <T extends Object> void rpush(String key, T value) {
        Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
            jedis.rpush(key.getBytes(), ByteArrayHelper.object2Bytes(value));
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
        } finally {
            if (jedis != null) {
                getJedisPool().returnResource(jedis);
            }
        }

    }

    @Override
    public <T extends Object> void set(String key, T value) {
        Jedis jedis = getJedisPool().getResource();
        try {
            jedis.set(key.getBytes(), ByteArrayHelper.object2Bytes(value));
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
        } finally {
            getJedisPool().returnResource(jedis);
        }

    }

    @Override
    public <T extends Object> void set(String key, T value, int seconds) {
        Jedis jedis = getJedisPool().getResource();
        try {
            jedis.setex(key.getBytes(), seconds, ByteArrayHelper.object2Bytes(value));
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
        } finally {
            getJedisPool().returnResource(jedis);
        }
    }

    @Override
    public void setJedisPool(Pool<Jedis> jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public void setStr(String key, String value) {
        Jedis jedis = getJedisPool().getResource();
        try {
            jedis.set(key, value);
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
        } finally {
            getJedisPool().returnResource(jedis);
        }

    }

    @Override
    public <T extends Object> Long zadd(String key, double score, T member) {
        Jedis jedis = getJedisPool().getResource();
        try {
            return jedis.zadd(key.getBytes(), score, ByteArrayHelper.object2Bytes(member));
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
            return null;
        } finally {
            getJedisPool().returnResource(jedis);
        }
    }

    @Override
    public Long zcard(String key) {
        Jedis jedis = getJedisPool().getResource();
        try {
            return jedis.zcard(key.getBytes());
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
            return null;
        } finally {
            getJedisPool().returnResource(jedis);
        }
    }

    @Override
    public Long zcount(String key, double min, double max) {
        Jedis jedis = getJedisPool().getResource();
        try {
            return jedis.zcount(key.getBytes(), min, max);
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
            return null;
        } finally {
            getJedisPool().returnResource(jedis);
        }
    }

    @Override
    public <T extends Object> Long zrank(String key, T member) {
        Jedis jedis = getJedisPool().getResource();
        try {
            return jedis.zrank(key.getBytes(), ByteArrayHelper.object2Bytes(member));
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
            return -1l;
        } finally {
            getJedisPool().returnResource(jedis);
        }
    }

    @Override
    public <T extends Object> Long zrem(String key, T member) {
        Jedis jedis = getJedisPool().getResource();
        try {
            return jedis.zrem(key.getBytes(), ByteArrayHelper.object2Bytes(member));
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
            return null;
        } finally {
            getJedisPool().returnResource(jedis);
        }
    }

    @Override
    public <T extends Object> List<T> zrevrange(String key, int start, int end) {
        Jedis jedis = getJedisPool().getResource();
        List<T> returnSet = new ArrayList<>();
        try {
            Set<byte[]> set = jedis.zrevrange(key.getBytes(), start, end);
            for (byte[] s : set) {
                returnSet.add((T)ByteArrayHelper.byte2Object(s));
            }
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
        } finally {
            if (jedis != null) {
                getJedisPool().returnResource(jedis);
            }
        }
        return returnSet;
    }

    @Override
    public <T extends Object> Long zrevrank(String key, T member) {
        Jedis jedis = getJedisPool().getResource();
        try {
            return jedis.zrevrank(key.getBytes(), ByteArrayHelper.object2Bytes(member)) == null ? -1 : jedis.zrevrank(
                    key.getBytes(), ByteArrayHelper.object2Bytes(member));
        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage());
            return -1l;
        } finally {
            getJedisPool().returnResource(jedis);
        }
    }
}