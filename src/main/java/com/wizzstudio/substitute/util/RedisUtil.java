package com.wizzstudio.substitute.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void storeNewCookie(String cookie, String userId) {
        redisTemplate.opsForValue().set(cookie, userId);
    }

    public String getCachedUserId(String cookie) {
        return redisTemplate.opsForValue().get(cookie);
    }

}
