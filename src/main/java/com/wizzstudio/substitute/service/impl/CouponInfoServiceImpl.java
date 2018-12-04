package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.CouponInfoDao;
import com.wizzstudio.substitute.domain.CouponInfo;
import com.wizzstudio.substitute.service.CouponInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created By Cx On 2018/12/4 11:33
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CouponInfoServiceImpl implements CouponInfoService {

    @Autowired
    CouponInfoDao couponInfoDao;

    @Override
    public CouponInfo findById(Integer couponId) {
        return couponInfoDao.findById(couponId).orElse(null);
    }
}
