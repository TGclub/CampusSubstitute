package com.wizzstudio.substitute.util;

import com.wizzstudio.substitute.constants.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.servlet.http.Cookie;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void storeNewCookie(String cookie, String userId) {
        redisTemplate.opsForValue().set(cookie, userId, Constant.TOKEN_EXPIRED, TimeUnit.SECONDS);
    }

    /**
     * 存储key，value，在expire分钟后过期
     */
    public void store(String key, String value, Integer expire) {
        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.MINUTES);
    }

    /**
     * 存储key，value，在expire分钟后过期
     */
    public void store(String key, String value, Integer expire, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, expire, timeUnit);
    }

    public void increment(String key, Double value) {
        redisTemplate.opsForValue().increment(key,value);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

}
