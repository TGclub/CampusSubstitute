package com.wizzstudio.substitute.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 接收前端 支付订单参数 表单
 * Created By Cx On 2018/11/6 23:15
 */
@Data
public class PayForm {
    //用户openid
    @NotBlank(message = "用户openid不能为空")
    private String userOpenid;
    //订单Id
    @NotNull
    private Integer indentId;
}
