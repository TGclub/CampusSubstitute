package com.wizzstudio.substitute.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Kikyou on 18-12-14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisUtilTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void storeNewCookie() {
        redisUtil.storeNewCookie("efwftest","EEETEE");
    }

}