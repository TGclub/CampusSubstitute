package com.wizzstudio.substitute.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created By Cx On 2018/12/5 9:42
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdminDaoTest {

    @Autowired
    AdminDao adminDao;

    @Test
    public void getAdminInfoByAdminId() {
    }

    @Test
    public void getAdminInfoByAdminName() {
    }

    @Test
    public void getAdminInfoByAdminRole() {
    }

    @Test
    public void findByAdminSchoolIdAndIsBoss() {
        System.out.println(adminDao.findByAdminSchoolIdAndIsBoss(3,true));
    }
}