package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.VO.CouponListVO;
import com.wizzstudio.substitute.domain.CouponInfo;
import com.wizzstudio.substitute.dto.UserCouponDTO;

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
     * 获取某用户所有已领取未过期 / 可领取的优惠券
     */
    CouponListVO findListByUserId(String userId);

    CouponInfo getSpecificCoupon(int id);

    /**
     * 返回首页轮播图中的优惠券信息
     */
    List<UserCouponDTO> getRecentFiveCouponInfo();

    /**
     * 通过优惠券id获取某优惠券信息
     */
    CouponInfo findByCouponId(Integer couponId);
}
