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

    public void storeNewCookie(String key, String value) {
        redisTemplate.opsForValue().set(key, value, Constant.TOKEN_EXPIRED, TimeUnit.SECONDS);
    }

    /**
     * 存储key，value，在expire分钟后过期
     */
    public void storeNewCookie(String key, String value, Integer expire) {
        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.MINUTES);
    }

    public String getCachedUserId(String cookie) {
        return redisTemplate.opsForValue().get(cookie);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

}
