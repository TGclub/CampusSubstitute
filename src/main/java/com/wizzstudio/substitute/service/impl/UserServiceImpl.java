package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dto.ApprenticeBasicInfo;
import com.wizzstudio.substitute.dto.ModifyUserInfoDTO;
import com.wizzstudio.substitute.dto.ResultDTO;
import com.wizzstudio.substitute.enums.Gender;
import com.wizzstudio.substitute.pojo.entity.User;
import com.wizzstudio.substitute.service.BaseService;
import com.wizzstudio.substitute.service.UserService;
import org.springframework.stereotype.Service;

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
    public User modifyUserInfo(Integer id, ModifyUserInfoDTO newInfo) {
        User user = userDao.getOne(id);
        Gender gender = newInfo.getGender();
        String school = newInfo.getSchool();
        Integer phoneNumber = newInfo.getPhoneNumber();
        String trueName = newInfo.getTrueName();
        String userName = newInfo.getUserName();
        if (gender != null) user.setGender(gender);
        if (school != null) user.setSchool(school);
        if (phoneNumber != null) user.setPhoneNumber(phoneNumber);
        if (trueName != null) user.setTrueName(trueName);
        if (userName != null) user.setUserName(userName);
        return entityManager.merge(user);
    }

    @Override
    public boolean addReferrer(Integer userId, Integer masterId) {
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
    public ApprenticeBasicInfo getApprenticeInfo(Integer userId) {
        return null;
    }
}
