package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.service.SchoolService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created By Cx On 2018/12/11 15:56
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SchoolServiceImplTest {

    @Autowired
    SchoolService schoolService;

    @Test
    public void getById() {
        System.out.println(schoolService.getById(null));
    }
}