package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.UserDao;
import com.wizzstudio.substitute.domain.CouponInfo;
import com.wizzstudio.substitute.domain.CouponRecord;
import com.wizzstudio.substitute.domain.User;
import com.wizzstudio.substitute.enums.ResultEnum;
import com.wizzstudio.substitute.exception.SubstituteException;
import com.wizzstudio.substitute.service.CouponInfoService;
import com.wizzstudio.substitute.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 通用校验service
 * Created By Cx On 2018/12/5 18:47
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CommonCheckService {
    @Autowired
    UserService userService;
    @Autowired
    CouponInfoService couponInfoService;

    /**
     * 通过userId检验用户是否存在，若存在返回User
     */
    public User checkUserByUserId(String userId) {
        User user = userService.findUserById(userId);
        if (user == null) {
            log.error("用户不存在，userId={}", userId);
            throw new SubstituteException(ResultEnum.USER_NOT_EXISTS);
        }
        return user;
    }

    /**
     * 通过userOpenid检验用户是否存在，若存在返回User
     */
    public User checkUserByOpenid(String openid) {
        User user = userService.findUserByOpenId(openid);
        if (user == null) {
            log.error("用户不存在，openid={}", openid);
            throw new SubstituteException(ResultEnum.USER_NOT_EXISTS);
        }
        return user;
    }

    public CouponInfo checkCouponInfoById(Integer couponId) {
        CouponInfo couponInfo = couponInfoService.findById(couponId);
        if (couponInfo == null) {
            log.error("优惠券不存在，couponId={}", couponId);
            throw new SubstituteException(ResultEnum.COUPON_NOT_EXISTS);
        }
        return couponInfo;
    }
}
