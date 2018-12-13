package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.domain.CouponInfo;

import java.util.List;

/**
 * Created By Cx On 2018/12/4 11:32
 */
public interface CouponInfoService {

    /**
     * 通过id获得优惠券信息
     */
    CouponInfo findById(Integer couponId);

    /**
     * 获取某用户所有未过期的优惠券
     */
    List<CouponInfo> findLiveByUserId(String userId);

    /**
     * 某用户使用某优惠券
     */
    void useCoupon(Integer couponId,String userId);
}
