package com.wizzstudio.substitute.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created By Cx On 2018/12/16 20:09
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CouponInfoDaoTest {

    @Autowired
    CouponInfoDao couponInfoDao;

    @Test
    public void findByCouponId() {
    }

    @Test
    public void findAllByCouponIdIsInAndIsDeletedIsFalse() {
    }

    @Test
    public void findAllGet() {
        List<Integer> d = new ArrayList<>();
        System.out.println(couponInfoDao.findAllGetExcept(d));
    }

    @Test
    public void findTop5ByInvalidTimeAfterAndIsDeletedIsFalseOrderByCouponIdDesc() {
    }
}