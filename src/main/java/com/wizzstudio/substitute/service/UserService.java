package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.dto.ApprenticeBasicInfo;
import com.wizzstudio.substitute.dto.ModifyUserInfoDTO;
import com.wizzstudio.substitute.dto.ResultDTO;
import com.wizzstudio.substitute.pojo.entity.User;

import java.util.List;

/**
 * 定义了用户相关的基本方法
 */
public interface UserService {

    /**
     *
     * @param user 用户信息
     * @return
     */
    User addNewUser(User user);

    /**
     * 通过openId获取用户信息
     * @param openId 用户openId
     * @return
     */
    User getUserInfo(String openId);

    /**
     * 更新用户信息
     * @param newInfo 可更新的用户信息
     */
    User modifyUserInfo(String id, ModifyUserInfoDTO newInfo);

    /**
     *添加推荐人
     * @param userId 用户id
     * @param masterId 师傅id
     * @return true 添加成功, false 添加失败
     */
    boolean addReferrer(String userId, String masterId);

    /**
     * 获取用户所有徒弟的基本信息
     * @param userId 徒弟id
     * @return 用户的基本信息
     */
    List<ApprenticeBasicInfo> getApprenticeInfo(String userId);

    User findUserByOpenId(String openid);

    User findUserById(String id);
}
