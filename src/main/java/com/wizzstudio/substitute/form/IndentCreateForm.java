package com.wizzstudio.substitute.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 发布订单验证表单
 * Created By Cx On 2018/11/7 11:21
 */
@Data
public class IndentCreateForm {

    //订单类型，帮我购：HELP_BUY，帮我递：HELP_SEND，随意帮：HELP_OTHER
    @NotBlank(message = "订单类型不能为空")
    private String indentType;

    //要求性别，男：”MALE”,女：”FEMALE”,不限：”NO_LIMITED”
    @NotBlank(message = "需求性别不能为空")
    private String requireGender;

    //下单用户openid
    @NotNull(message = "下单用户openid不能为空")
    private String publisherOpenid;

    //联系电话
    @NotNull(message = "用户联系电话不能为空")
    private Long publisherPhone;

    //订单内容
    @NotBlank(message = "订单内容不能为空")
    private String indentContent;

    //订单悬赏金
    @NotNull(message = "订单悬赏金不能为空")
    private BigDecimal indentPrice;

    //联系人姓名，订单类型非随意帮时必填
    private String publisherName;

    //取货地址ID，订单类型非随意帮时必填
    private Integer takeGoodAddressId;

    //送达地址ID，订单类型为帮我递时必填
    private Integer shippingAddressId;

    //物品金额，订单类型为帮我购时必填
    private BigDecimal goodPrice;

    //快递公司名，订单类型为帮我递时可填
    private String companyName;

    //取件码，订单类型为帮我递时可填
    private String pickupCode;
}
