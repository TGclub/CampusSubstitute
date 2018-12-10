package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created By Cx On 2018/12/4 13:09
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Slf4j
public class AddressServiceImplTest {

    @Autowired
    AddressService addressService;

    @Before
    public void out(){
        System.out.println("============================================================");
    }

    @Test
    public void addUsualAddress() {
        log.info("hello");
    }

    @Test
    public void getById() {
        System.out.println(addressService.getById(3));
    }

    @Test
    public void getUsualAddress() {
    }

    @Test
    public void getAllByAddress() {
    }

    @Test
    public void getSchoolInFuzzyMatching() {
    }
}