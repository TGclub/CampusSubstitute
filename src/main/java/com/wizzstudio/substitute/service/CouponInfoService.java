package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.domain.CouponInfo;

/**
 * Created By Cx On 2018/12/4 11:32
 */
public interface CouponInfoService {

    /**
     * 通过id获得优惠券信息
     */
    CouponInfo findById(Integer couponId);
}
