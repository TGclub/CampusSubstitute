package com.wizzstudio.substitute.VO;

import com.wizzstudio.substitute.enums.GenderEnum;
import com.wizzstudio.substitute.enums.indent.IndentStateEnum;
import com.wizzstudio.substitute.enums.indent.IndentTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Kikyou on 18-12-18
 */
@Data
@NoArgsConstructor
public class UrgentIndentVO implements Serializable {
    private Integer indentId;

    //送货人id
    private String performerId;

    //下单用户id
    private String publisherId;

    //优惠券ID,可能为空
    private Integer couponId;

    //订单类型，帮我购：HELP_BUY，帮我递：HELP_SEND，随意帮：HELP_OTHER
    private IndentTypeEnum indentType;

    //订单要求性别，男：”MALE”,女：”FEMALE”,不限：”NO_LIMITED”
    private GenderEnum requireGender;

    //任务内容
    private String indentContent;

    //订单悬赏金
    private Integer indentPrice;

    //订单优惠额，单位元，默认0元
    private Integer couponPrice;

    //实付金额,单位元
    private Integer totalPrice;

    //加急类型，0:不加急，1:超时 2:退单
    //columnDefinition表示该字段在数据库中的实际类型。不使用的话，当ddl-auto设为validate会报错
    private Integer urgentType;

    //加急订单是否已处理，0：否，1：是，默认为0
    private Boolean isSolved;

    //订单状态,待支付：WAIT_PAY待接单：WAIT_FOR_PERFORMER，执行中：PERFORMING，货物已送达：ARRIVED，已完成：COMPLETED
    private IndentStateEnum indentState;

    //取货地址，订单类型非随意帮时必填
    private String takeGoodAddress;

    //送达地点ID，订单类型为帮我递时必填
    private Integer shippingAddressId;

    //隐私信息，仅订单类型为帮我递时非空
    private String secretText;

    //物品金额，仅订单类型为帮我购时非空
    private BigDecimal goodPrice;

    //创建时间
    private Date createTime;

    //最近更新时间
    private Date updateTime;

    private String userName;

    private String trueName;

    private Long phone;

    private String school;
}
