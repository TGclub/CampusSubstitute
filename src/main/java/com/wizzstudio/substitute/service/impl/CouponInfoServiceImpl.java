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
        //1、获取已领取且可用的优惠券
        //1.1获取所有已领取信息
        List<CouponRecord> couponRecords = couponRecordService.findGetByUserId(userId);
        List<Integer> couponIds = new ArrayList<>();
        couponRecords.forEach(x -> {
            if (!x.getIsUsed()) {
                //如果未被使用，添加到liveCoupon列表中
                couponIds.add(x.getCouponId());
            }
        });
        List<CouponInfo> liveCouponInfos = couponInfoDao.findAllByCouponIdIsInAndIsDeletedIsFalse(couponIds);
        couponListVO.setLiveCoupons(liveCouponInfos);
        //2、获取未领取但可领取的优惠券
        //获取该用户已领取的优惠券Id
        List<Integer> couponGetIds = couponRecords.stream().map(CouponRecord::getCouponId).collect(Collectors.toList());
        //获取该用户所有可领取优惠券
        List<CouponInfo> couponInfos;
        if (couponGetIds.size() == 0) {
            couponInfos = couponInfoDao.findAllGet();
        } else {
            couponInfos = couponInfoDao.findAllGetExcept(couponGetIds);
        }
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
        couponInfos.forEach(x -> {
            UserCouponDTO userCouponDTO = new UserCouponDTO();
            BeanUtils.copyProperties(x, userCouponDTO);
            if (x.getPicture() != null) {
                userCouponDTO.setPictureLink("https://bang.zhengsj.top/coupon/img/" + x.getCouponId());
            }
            userCouponDTOS.add(userCouponDTO);
        });
        return userCouponDTOS;
    }

    @Override
    public CouponInfo findByCouponId(Integer couponId) {
        return couponInfoDao.findByCouponId(couponId);
    }
}
