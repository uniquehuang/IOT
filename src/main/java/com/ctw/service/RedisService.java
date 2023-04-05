package com.ctw.service;


import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class RedisService {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 检查key是否存在
     *
     * @param key 键
     * @return 存在为true
     */
    public boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 根据key获取缓存中的值
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 向redis里存入键值对
     * @param key 键
     * @param value 值
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 向redis里存入数据和设置缓存（过期）时间，单位为秒
     * @see #set(String, String, long, TimeUnit)
     */
    public void set(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 向redis里存入数据和设置缓存（过期）时间
     *
     * @param key 键
     * @param value 值
     * @param timeout 过期时间
     * @param unit 时间单位
     */
    public void set(String key, String value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 根据key获取过期时间并换算成指定单位
     * @param key 键
     * @param unit 时间单位
     * @return 过期时间
     */
    public long getExpire(String key, TimeUnit unit) {
        return stringRedisTemplate.getExpire(key, unit);
    }

    /**
     * 根据key获取过期时间，默认为秒
     * @param key 键
     * @return 过期时间（秒）
     */
    public long getExpire(String key) {
        return getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 根据key删除缓存
     * @param key 键
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }
}
