package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.CouponRecordDao;
import com.wizzstudio.substitute.domain.CouponInfo;
import com.wizzstudio.substitute.domain.CouponRecord;
import com.wizzstudio.substitute.enums.ResultEnum;
import com.wizzstudio.substitute.exception.SubstituteException;
import com.wizzstudio.substitute.service.CouponRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created By Cx On 2018/12/4 11:17
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CouponRecordServiceImpl implements CouponRecordService {

    @Autowired
    CouponRecordDao couponRecordDao;
    @Autowired
    CommonCheckService commonCheckService;

    @Override
    public CouponRecord findByOwnerAndCouponId(String ownerId, Integer couponId) {
        return couponRecordDao.findByCouponIdAndOwnerId(couponId, ownerId);
    }

    @Override
    public void update(CouponRecord couponRecord) {
        couponRecordDao.save(couponRecord);
    }

    @Override
    public void create(String userId, Integer couponId) {
        //获取领取记录信息，若用户已领取则不可重复领取
        CouponRecord couponRecord = couponRecordDao.findByCouponIdAndOwnerId(couponId, userId);
        if (couponRecord != null) {
            log.error("已领取过该优惠券，userId={}，couponId={}", userId, couponId);
            throw new SubstituteException("已领取过该优惠券");
        }
        //校验用户是否存在
        commonCheckService.checkUserByUserId(userId);
        //获取优惠券信息，从而获取失效时间
        CouponInfo couponInfo = commonCheckService.checkCouponInfoById(couponId);
        couponRecord = CouponRecord.builder().couponId(couponId).ownerId(userId).invalidTime(couponInfo.getInvalidTime()).build();
        couponRecordDao.save(couponRecord);
    }

    @Override
    public List<CouponRecord> findLiveByUserId(String userId) {
        commonCheckService.checkUserByUserId(userId);
        return couponRecordDao.findLiveByUserId(userId);
    }

    @Override
    public List<CouponRecord> findGetByUserId(String userId) {
        commonCheckService.checkUserByUserId(userId);
        return couponRecordDao.findGetByUserId(userId);
    }
}
