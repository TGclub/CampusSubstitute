package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.CouponInfoDao;
import com.wizzstudio.substitute.domain.CouponInfo;
import com.wizzstudio.substitute.domain.CouponRecord;
import com.wizzstudio.substitute.dto.UserCouponDTO;
import com.wizzstudio.substitute.service.CouponInfoService;
import com.wizzstudio.substitute.service.CouponRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public CouponInfo getSpecificCoupon(int id) {
        return couponInfoDao.findByCouponId(id);
    }

    @Override
    public List<UserCouponDTO> getRecentFiveCouponInfo() {
        List<CouponInfo> couponInfos = couponInfoDao.findTop5ByInvalidTimeAfterAndIsDeletedIsFalseOrderByCouponIdDesc(new Date());
        List<UserCouponDTO> userCouponDTOS = new ArrayList<>();
        couponInfos.forEach( x->{
            UserCouponDTO userCouponDTO = new UserCouponDTO();
            BeanUtils.copyProperties(x, userCouponDTO);
            if (x.getPicture() != null) {
                userCouponDTO.setPictureLink("https://bang.zhengsj.top/coupon/img/"+x.getCouponId());
            }
        });
        return userCouponDTOS;
    }
}
