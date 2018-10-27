package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.UserDao;
import com.wizzstudio.substitute.dto.ModifyUserInfoDTO;
import com.wizzstudio.substitute.enums.Gender;
import com.wizzstudio.substitute.enums.Role;
import com.wizzstudio.substitute.pojo.User;
import com.wizzstudio.substitute.util.KeyUtil;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.*;

@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserDao userDao;
    @Rollback(false)
    //@RepeatedTest(20)
    @Test
    public void addNewUser() {
        User user = User.newBuilder()
                .setGender(Gender.MALE)
                .setId(KeyUtil.getUserUniqueKey())
                .setOpenid(UUID.randomUUID().toString())
                .setAvatar("TEST")
                .setAllIncome(new BigDecimal(0))
                .setBalance(new BigDecimal(0))
                .setMasterIncome(new BigDecimal(0))
                .setUserName("TEST")
                .setPhone(new Random().nextLong())
                .setRole(Role.ROLE_USER)
                .build();
        userDao.save(user);
    }

    @Test
    @AfterAll
    public void getUserInfo() {
        User user = userDao.getOne("EEETEE");
        System.out.println(user.getOpenid());
    }

    @Test
    @Rollback(true)
    public void modifyUserInfo() {

        String id = "EETEE";
        ModifyUserInfoDTO newInfo = new ModifyUserInfoDTO();
        User user = userDao.getOne(id);
        user.setGender(Gender.FAMALE);
        user.setSchool("beilei");
        Gender gender = newInfo.getGender();
        String school = newInfo.getSchool();
        Long phoneNumber = newInfo.getPhoneNumber();
        String trueName = newInfo.getTrueName();
        String userName = newInfo.getUserName();
        if (gender != null) user.setGender(gender);
        if (school != null) user.setSchool(school);
        if (phoneNumber != null) user.setPhone(phoneNumber);
        if (trueName != null) user.setTrueName(trueName);
        if (userName != null) user.setUserName(userName);
        entityManager.merge(user);
    }


    @Test
    public void addReferrer() {
    }

    @Test
    public void getApprenticeInfo() {
    }

    @Test
    public void findUserByOpenId() {
    }


}