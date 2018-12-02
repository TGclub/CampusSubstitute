package com.wizzstudio.substitute.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wizzstudio.substitute.enums.GenderEnum;
import com.wizzstudio.substitute.enums.indent.IndentStateEnum;
import com.wizzstudio.substitute.enums.indent.IndentTypeEnum;
import com.wizzstudio.substitute.util.serializer.Date2LongSerializer;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//订单
@Entity
@Data
@DynamicInsert
public class Indent implements Serializable {

    //订单id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer indentId;

    //送货人id
    private String performerId;

    //下单用户id
    @NotNull
    private String publisherId;

    //优惠券ID,可能为空
    private Integer couponId;

    //订单类型，帮我购：HELP_BUY，帮我递：HELP_SEND，随意帮：HELP_OTHER
    @Enumerated(EnumType.STRING)
    private IndentTypeEnum indentType;

    //订单要求性别，男：”MALE”,女：”FEMALE”,不限：”NO_LIMITED”
    @Enumerated(value = EnumType.STRING)
    private GenderEnum requireGender;

    //任务内容
    @NotNull
    private String indentContent;

    //订单悬赏金
    @NotNull
    private Integer indentPrice;

    //订单优惠额，单位元，默认0元
    private Integer couponPrice;

    //实付金额,单位元
    private Integer totalPrice;

    //加急类型，0:不加急，1:超时 2:退单
    //columnDefinition表示该字段在数据库中的实际类型。不使用的话，当ddl-auto设为validate会报错
    @Column(columnDefinition = "TINYINT")
    private Integer urgentType;

    //加急订单是否已处理，0：否，1：是，默认为0
    private Boolean isSolved;

    //订单状态,待支付：WAIT_PAY待接单：WAIT_FOR_PERFORMER，执行中：PERFORMING，货物已送达：ARRIVED，已完成：COMPLETED
    @Enumerated(EnumType.STRING)
    private IndentStateEnum indentState;

    //取货地址，订单类型非随意帮时必填
    private String takeGoodAddress;

    //送达地点ID，订单类型为帮我递时必填
    private Integer shippingAddressId;

    //隐私信息，仅订单类型为帮我递时非空
    private String secretText;

    //物品金额，仅订单类型为帮我购时非空
    private Integer goodPrice;

    //创建时间
    @Column(updatable = false, insertable = false)
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    //最近更新时间
    @Column(updatable = false, insertable = false)
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
}
