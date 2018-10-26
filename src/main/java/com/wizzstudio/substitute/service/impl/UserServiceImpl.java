package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dto.ApprenticeBasicInfo;
import com.wizzstudio.substitute.dto.ModifyUserInfoDTO;
import com.wizzstudio.substitute.enums.Gender;
import com.wizzstudio.substitute.pojo.User;
import com.wizzstudio.substitute.service.BaseService;
import com.wizzstudio.substitute.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends BaseService implements UserService {

    @Override
    public User addNewUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User getUserInfo(String openId) {
        return userDao.findByOpenid(openId);
    }

    @Override
    public User modifyUserInfo(String id, ModifyUserInfoDTO newInfo) {
        User user = findUserById(id);
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
        return entityManager.merge(user);
    }

    @Override
    public boolean addReferrer(String userId, String masterId) {
        User master = userDao.getOne(masterId);
        User user = userDao.getOne(userId);
        if (master != null) {
            user.setMasterId(masterId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<ApprenticeBasicInfo> getApprenticeInfo(String userId) {
        Query query = entityManager.createNamedQuery
                ("getAllApprentice", User.class).setParameter("account", userId);
        List<User> apprentices = (List<User>) query.getResultList();
        List<ApprenticeBasicInfo> basicInfos = new ArrayList<>();
        apprentices.forEach(x -> {
            ApprenticeBasicInfo basicInfo = new ApprenticeBasicInfo();
            BeanUtils.copyProperties(apprentices, basicInfo);
            basicInfos.add(basicInfo);
        });
        return basicInfos;

    }

    @Override
    public User findUserByOpenId(String openid) {
        return userDao.findByOpenid(openid);
    }

    @Override
    public User findUserById(String id) {
        return userDao.getOne(id);
    }
}
