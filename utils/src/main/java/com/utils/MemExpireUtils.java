package com.utils;

import java.util.Date;
import java.util.Random;

/**
 * @author ripflowers
 */
public class MemExpireUtils {

    private static final Random halfAnHourRandom = new Random();

    public static final long MEMCACHED_EXPIRED_ONE_MINUTES = 60 * 1000;
    public static final long MEMCACHED_EXPIRED_ONE_HOUR = 60 * MEMCACHED_EXPIRED_ONE_MINUTES;
    public static final long MEMCACHED_EXPIRED_ONE_DAY = 24 * MEMCACHED_EXPIRED_ONE_HOUR;
    public static final long MEMCACHED_EXPIRED_ONE_WEEK = 7 * MEMCACHED_EXPIRED_ONE_DAY;
    public static final long MEMCACHED_EXPIRED_ONE_MONTH = 30 * MEMCACHED_EXPIRED_ONE_DAY;

    public static Date addRandom10Min(long expireTime) {
        return new Date(expireTime + halfAnHourRandom.nextInt(600000));
    }

    public static Date addRandom30Min(long expireTime) {
        return new Date(expireTime + halfAnHourRandom.nextInt(1800000));
    }

    public static Date addRandom60Min(long expireTime) {
        return new Date(expireTime + halfAnHourRandom.nextInt(3600000));
    }
}
