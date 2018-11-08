package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.UserDao;
import com.wizzstudio.substitute.dto.ModifyUserInfoDTO;
import com.wizzstudio.substitute.dto.UserBasicInfo;
import com.wizzstudio.substitute.enums.Gender;
import com.wizzstudio.substitute.pojo.User;
import com.wizzstudio.substitute.util.RandomUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl service;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserDao userDao;
    @Rollback(false)
    @Test
    public void addNewUser() {
        User user = User.newBuilder()
                .setGender(Gender.MALE)
                .setId(RandomUtil.getSixRandom())
                .setOpenid(UUID.randomUUID().toString())
                .setAvatar("TEST")
                .setAllIncome(new BigDecimal(0))
                .setBalance(new BigDecimal(0))
                .setMasterIncome(new BigDecimal(0))
                .setUserName("TEST")
                .setPhone(new Random().nextLong())
                .build();
        userDao.save(user);
    }




    @Test
    public void getBasicInfo() {
        service.getBasicInfo(new UserBasicInfo(), "EEETEE");
        service.getBasicInfo(new ArrayList<>(), "EEETEE");
    }


    @Test
    @Rollback(true)
    public void modifyUserInfo() {

        String id = "EETEE";
        ModifyUserInfoDTO newInfo = new ModifyUserInfoDTO();
        User user = userDao.getOne(id);
        user.setGender(Gender.FEMALE);
        user.setSchoolId(333);
        Gender gender = newInfo.getGender();
        Integer school = newInfo.getSchool();
        Long phoneNumber = newInfo.getPhoneNumber();
        String trueName = newInfo.getTrueName();
        String userName = newInfo.getUserName();
        if (gender != null) user.setGender(gender);
        if (school != null) user.setSchoolId(532);
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