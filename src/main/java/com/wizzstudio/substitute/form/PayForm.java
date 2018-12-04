package com.wizzstudio.substitute.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 接收前端 支付订单参数 表单
 * Created By Cx On 2018/11/6 23:15
 */
@Data
public class PayForm {
    //用户id
    @NotBlank(message = "用户id不能为空")
    private String userId;
    //充值金额,单位：元
    @NotNull(message = "充值金额不能为空")
    private BigDecimal totalFee;
}
