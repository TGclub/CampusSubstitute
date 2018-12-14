package com.wizzstudio.substitute.VO;

import com.wizzstudio.substitute.domain.CouponInfo;
import lombok.Data;

import java.util.List;

/**
 * Created By Cx On 2018/12/14 20:18
 */
@Data
public class CouponListVO {

    /**
     * 可用优惠券列表
     */
    private List<CouponInfo> liveCoupons;

    /**
     * 可领取优惠券列表
     */
    private List<CouponInfo> getCoupons;
}
