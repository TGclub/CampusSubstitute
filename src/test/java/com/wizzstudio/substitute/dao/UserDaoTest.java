package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;


@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;
    @Test
    public void getAllApprenticeById() {
        List<User> users= userDao.findByMasterId("EEETEE");
        System.out.println("size: " + users.size());
    }
}