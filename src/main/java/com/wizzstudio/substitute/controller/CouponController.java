package com.wizzstudio.substitute.controller;

import com.wizzstudio.substitute.domain.CouponInfo;
import com.wizzstudio.substitute.service.CouponInfoService;
import com.wizzstudio.substitute.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

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

    @ApiOperation("返回指定优惠券的图片， 例如")
    @ApiImplicitParam(name = "id", value = "指定优惠券id", example = "localhost:2333/admin/coupon/img/3")
    @ApiResponses({
            @ApiResponse(code = 200, message = "200请求成功，返回二进制图片")


    })
    @GetMapping("/img/{id}")
    public void getImg(@PathVariable int id, HttpServletResponse response) throws IOException {
        IOUtils.copy(new ByteArrayInputStream(couponInfoService.getSpecificCoupon(id).getPicture()),
                response.getOutputStream());
    }

    @GetMapping("/list")
    public ResponseEntity getCouponList() {
        return ResultUtil.success(couponInfoService.getCouponInfo());
    }
}
