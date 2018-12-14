package com.wizzstudio.substitute.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created By Cx On 2018/12/15 0:17
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RedisUtilTest {

    @Autowired
    RedisUtil redisUtil;

    @Before
    public void out(){
        System.out.println("============================================================");
    }

    @Test
    public void store(){
        redisUtil.store("3","44",8888);
        redisUtil.increment("3",22.5);
    }

    @Test
    public void store2(){

    }

}