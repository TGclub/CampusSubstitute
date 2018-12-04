package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.CouponRecordDao;
import com.wizzstudio.substitute.domain.CouponRecord;
import com.wizzstudio.substitute.service.CouponRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created By Cx On 2018/12/4 11:17
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CouponRecordServiceImpl implements CouponRecordService {

    @Autowired
    CouponRecordDao couponRecordDao;

    @Override
    public CouponRecord findByOwnerAndCouponId(String ownerId, Integer couponId) {
        return couponRecordDao.findByCouponIdAndOwnerId(couponId,ownerId);
    }

    @Override
    public void save(CouponRecord couponRecord) {
        couponRecordDao.save(couponRecord);
    }
}
