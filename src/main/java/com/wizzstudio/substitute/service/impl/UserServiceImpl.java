package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dto.UserBasicInfo;
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
        User master = findUserById(masterId);
        User user = findUserById(userId);
        if (master != null && user != null) {
            user.setMasterId(masterId);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取徒弟或师傅的基本信息
     * @param returnType 传入一个实例指定返回类型，list作为徒弟处理，UserBasicInfo作为师傅处理
     * @param userId 徒弟id
     * @return 返回一个list或者UserBasicInfo
     */
    @Override
    @SuppressWarnings(value = "unchecked")
    public <T> T getBasicInfo(T returnType, String userId) {
        User user = findUserById(userId);
        if (user == null) return null;
        if (returnType instanceof List) {
            Query query = entityManager.createNamedQuery
                    ("getAllApprentice", User.class).setParameter("account", userId);
            List<User> apprentices = (List<User>) query.getResultList();
            List<UserBasicInfo> basicInfos = new ArrayList<>();
            apprentices.forEach(x -> {
                UserBasicInfo basicInfo = new UserBasicInfo();
                BeanUtils.copyProperties(apprentices, basicInfo);
                basicInfos.add(basicInfo);
            });
            return (T)basicInfos;
        } else if (returnType instanceof UserBasicInfo) {
            String masterId = user.getMasterId();
            if (masterId != null) {
                User master = findUserById(masterId);
                UserBasicInfo basicInfo = new UserBasicInfo();
                BeanUtils.copyProperties(master, basicInfo);
                return (T) basicInfo;
            } else {
                return null;
            }
        } else {
            return null;
        }


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
