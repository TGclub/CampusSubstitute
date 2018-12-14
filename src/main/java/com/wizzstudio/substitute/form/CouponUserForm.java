package com.wizzstudio.substitute.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created By Cx On 2018/12/13 23:04
 */
@Data
public class CouponUserForm {
    @NotNull(message = "用户Id不能为空")
    String userId;

    @NotNull(message = "优惠券Id不能为空")
    Integer couponId;
}
