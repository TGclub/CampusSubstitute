package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.VO.CouponListVO;
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
    public CouponListVO findListByUserId(String userId) {
        CouponListVO couponListVO = new CouponListVO();
        //获取已领取可用优惠券
        List<CouponRecord> couponRecords = couponRecordService.findLiveByUserId(userId);
        List<Integer> couponIds = couponRecords.stream().map(CouponRecord::getCouponId).collect(Collectors.toList());
        List<CouponInfo> liveCouponInfos = couponInfoDao.findAllByCouponIdIsIn(couponIds);
        couponListVO.setLiveCoupons(liveCouponInfos);
        //获取未领取但可领取的优惠券
        List<CouponInfo> couponInfos = couponInfoDao.findAllLive();
        couponInfos.removeAll(liveCouponInfos);
        couponListVO.setGetCoupons(couponInfos);
        return couponListVO;
    }

    @Override
    public CouponInfo getSpecificCoupon(int id) {
        return couponInfoDao.findByCouponId(id);
    }

    @Override
    public List<UserCouponDTO> getRecentFiveCouponInfo() {
        List<CouponInfo> couponInfos = couponInfoDao.findTop5ByInvalidTimeAfterAndIsDeletedIsFalseOrderByCouponIdDesc(new Date());
        log.info("coupon size: " + couponInfos.size());
        List<UserCouponDTO> userCouponDTOS = new ArrayList<>();
        couponInfos.forEach( x->{
            UserCouponDTO userCouponDTO = new UserCouponDTO();
            BeanUtils.copyProperties(x, userCouponDTO);
            if (x.getPicture() != null) {
                userCouponDTO.setPictureLink("https://bang.zhengsj.top/coupon/img/"+x.getCouponId());
            }
            userCouponDTOS.add(userCouponDTO);
        });
        return userCouponDTOS;
    }
}
