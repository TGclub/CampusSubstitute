package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.UserDao;
import com.wizzstudio.substitute.dto.UserBasicInfo;
import com.wizzstudio.substitute.dto.ModifyUserInfoDTO;
import com.wizzstudio.substitute.enums.GenderEnum;
import com.wizzstudio.substitute.domain.User;
import com.wizzstudio.substitute.service.UserService;
import com.wizzstudio.substitute.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    UserDao userDao;

    /**
     * 获取一个未被使用过的用户Id
     */
    public String getUserUniqueKey(){
        String userId = RandomUtil.getSixRandom();
        while (findUserById(userId) != null) {
            userId = RandomUtil.getSixRandom();
        }
        return userId;
    }

    @Override
    public User saveUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User getByOpenid(String openid) {
        return userDao.findByOpenid(openid);
    }

    @Override
    public void modifyUserInfo(String id, ModifyUserInfoDTO newInfo) {
        User user = findUserById(id);
        GenderEnum gender = newInfo.getGender();
        Integer school = newInfo.getSchool();
        Long phoneNumber = newInfo.getPhoneNumber();
        String trueName = newInfo.getTrueName();
        String userName = newInfo.getUserName();
        if (gender != null) user.setGender(gender);
        if (school != null) user.setSchoolId(school);
        if (phoneNumber != null) user.setPhone(phoneNumber);
        if (trueName != null) user.setTrueName(trueName);
        if (userName != null) user.setUserName(userName);
        userDao.save(user);

    }

    @Override
    public boolean addReferrer(String userId, String masterId) {
        User master = findUserById(masterId);
        User user = findUserById(userId);
        if (master != null && user != null) {
            user.setMasterId(masterId);
            userDao.save(user);
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
            List<UserBasicInfo> basicInfoList = new ArrayList<>();
            apprentices.forEach(x -> {
                UserBasicInfo basicInfo = new UserBasicInfo();
                BeanUtils.copyProperties(x, basicInfo);
                basicInfoList.add(basicInfo);
            });
            return (T)basicInfoList;
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
        return userDao.findUserById(id);
    }

    @Override
    public void reduceBalance(String userId, BigDecimal number) {
        User user = findUserById(userId);
        if (user.getBalance().compareTo(number) < 0){
            log.error("【用户支付】支付失败，用户余额不足，balance={},reduceNumber={}",user.getBalance(),number);
            throw new SecurityException("支付失败，用户余额不足");
        }
        //扣钱并保存信息
        user.setBalance(user.getBalance().subtract(number));
        saveUser(user);
    }


}
