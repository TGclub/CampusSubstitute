package com.wizzstudio.substitute.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created By Cx On 2018/11/21 22:18
 */
@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class IndentDaoTest {

    @Autowired
    IndentDao indentDao;

    @Test
    public void findByIndentId() {
    }

    @Test
    public void findByPerformerId() {
    }

    @Test
    public void findByPublisherOpenid() {
    }

    @Test
    public void findWaitByIndentContentLike() {
        List<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        System.out.println("================================================================");
        System.out.println(indentDao.findWaitByShippingAddressIdInOrderByDefault(a));
    }

    @Test
    public void findAllByIndentContentLikeOrderByIndentPriceDesc() {
        System.out.println("================================================================");
    }

    @Test
    public void findAllByIndentState() {
    }

    @Test
    public void findAllByIndentStateOrderByIndentPriceDesc() {
    }

    @Test
    public void findAllByIndentStateOrderByCreateTimeDesc() {
    }
}