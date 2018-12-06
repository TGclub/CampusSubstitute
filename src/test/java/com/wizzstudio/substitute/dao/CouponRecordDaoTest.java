package com.wizzstudio.substitute.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created By Cx On 2018/12/5 19:40
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CouponRecordDaoTest {

    @Autowired
    CouponRecordDao couponRecordDao;

    @Test
    public void findByCouponIdAndOwnerId() {
    }

    @Test
    public void findLiveByUserId() {
        System.out.println(couponRecordDao.findLiveByUserId("aaaaaa"));
    }
}