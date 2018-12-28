package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.domain.CouponRecord;

import java.util.List;

/**
 * Created By Cx On 2018/12/4 11:15
 */
public interface CouponRecordService {

    /**
     * 通过用户id和优惠券id，查询某用户是否有领取该优惠券的记录
     * 若有，返回该优惠券领取的信息
     */
    CouponRecord findByOwnerAndCouponId(String ownerId, Integer couponId);

    /**
     * 更新优惠券领取信息
     */
    void update(CouponRecord couponRecord);

    /**
     * 添加优惠券领取信息
     */
    void create(String userId, Integer couponId);

    /**
     * 获取所有该用户领取的未过期的优惠券
     */
    List<CouponRecord> findLiveByUserId(String userId);

    /**
     * 获取所有该用户领取的所有优惠券
     */
    List<CouponRecord> findGetByUserId(String userId);
}
