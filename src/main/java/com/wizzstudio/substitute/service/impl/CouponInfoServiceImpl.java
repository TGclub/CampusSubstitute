package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.CouponInfoDao;
import com.wizzstudio.substitute.domain.CouponInfo;
import com.wizzstudio.substitute.domain.CouponRecord;
import com.wizzstudio.substitute.service.CouponInfoService;
import com.wizzstudio.substitute.service.CouponRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created By Cx On 2018/12/4 11:33
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CouponInfoServiceImpl implements CouponInfoService {

    @Autowired
    CouponInfoDao couponInfoDao;
    @Autowired
    CouponRecordService couponRecordService;

    @Override
    public CouponInfo findById(Integer couponId) {
        return couponInfoDao.findById(couponId).orElse(null);
    }

    @Override
    public List<CouponInfo> findLiveByUserId(String userId) {
        List<CouponRecord> couponRecords = couponRecordService.findLiveByUserId(userId);
        List<Integer> couponIds = couponRecords.stream().map(CouponRecord::getCouponId).collect(Collectors.toList());
        return couponInfoDao.findAllByCouponIdIsIn(couponIds);
    }
}
