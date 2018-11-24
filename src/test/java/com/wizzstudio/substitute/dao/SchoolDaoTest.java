package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.domain.School;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

/**
 * Created By Cx On 2018/11/24 12:32
 */
@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class SchoolDaoTest {

    @Autowired
    SchoolDao schoolDao;

    @Test
    public void findBySchoolNameLike() {
    }

    @Test
    public void save(){
        School x = new School();
        x.setSchoolName("xxx");
        schoolDao.save(x);
        System.out.println("==============================================");
        System.out.println(x);

    }
}