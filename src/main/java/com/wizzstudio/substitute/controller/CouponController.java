package com.wizzstudio.substitute.controller;

import com.wizzstudio.substitute.service.CouponInfoService;
import com.wizzstudio.substitute.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 优惠券有关接口
 * Created By Cx On 2018/12/5 19:21
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    CouponInfoService couponInfoService;

    /**
     * 获取某用户未过期的优惠券列表
     */
    @GetMapping("/list/{userId}")
    public ResponseEntity findLiveByUserId(@PathVariable String userId){
        return ResultUtil.success(couponInfoService.findLiveByUserId(userId));
    }

    /**
     * 某用户领取某优惠券
     */
    @PostMapping("/get/{userId}/{couponId}")
    public ResponseEntity getCoupon(@PathVariable String userId,@PathVariable Integer couponId){
        //todo
        return ResultUtil.success();
    }
}
