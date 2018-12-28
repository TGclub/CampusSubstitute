package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.domain.CouponInfo;
import com.wizzstudio.substitute.dto.ModifyUserInfoDTO;
import com.wizzstudio.substitute.domain.User;
import com.wizzstudio.substitute.dto.wx.WxInfo;
import me.chanjar.weixin.common.exception.WxErrorException;

import java.math.BigDecimal;
import java.util.List;


/**
 * 定义了用户相关的基本方法
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param loginData 用户登录所需信息
     * @return 用户信息
     */
    User userLogin(WxInfo loginData) throws WxErrorException;

    /**
     * 新增或修改用户信息
     *
     * @param user 用户信息
     * @return 用户信息
     */
    User saveUser(User user);

    /**
     * 更新用户信息
     *
     * @param newInfo 可更新的用户信息
     */
    void modifyUserInfo(String id, ModifyUserInfoDTO newInfo);

    /**
     * 添加推荐人
     *
     * @param userId   用户id
     * @param masterId 师傅id
     * @return true 添加成功, false 添加失败
     */
    boolean addReferrer(String userId, String masterId);

    /**
     * 获取用户所有徒弟的基本信息
     *
     * @param userId 徒弟id
     * @param type   指定返回类型
     * @return 用户的基本信息
     */
    <T> T getBasicInfo(T type, String userId);

    /**
     * 通过openId获取用户信息
     *
     * @param openid 用户openid
     */
    User findUserByOpenId(String openid);

    /**
     * 通过Id获取用户信息
     */
    User findUserById(String id);

    /**
     * 扣除某用户number的余额量
     *
     * @param userId 用户id
     * @param number 减少金额量
     */
    void reduceBalance(String userId, BigDecimal number);

    /**
     * 获取用户当日作为推荐人的收益
     */
    String getMasterTodayIncome(String userId);
}
