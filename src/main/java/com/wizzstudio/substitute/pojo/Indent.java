package com.wizzstudio.substitute.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wizzstudio.substitute.enums.Gender;
import com.wizzstudio.substitute.enums.IndentState;
import com.wizzstudio.substitute.enums.IndentTypeEnum;
import com.wizzstudio.substitute.util.serializer.Date2LongSerializer;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//订单
@Entity
@Data
public class Indent implements Serializable {

    //订单id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer indentId;

    //送货人id
    private String performerId;

    //收货人id
    @NotNull
    private String publisherId;

    //联系人电话,不能用publisherId查，因为可能不同
    @NotNull
    private Long publisherPhone;

    //订单类型，帮我购：HELP_BUY，帮我递：HELP_SEND，随意帮：HELP_OTHER
    @NotNull
    @Enumerated(EnumType.STRING)
    private IndentTypeEnum indentType;

    //订单要求性别，男：”MALE”,女：”FAMALE”,不限：”NO_LIMITED”
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Gender requireGender;

    //任务内容
    @NotNull
    private String indentContent;

    //订单悬赏金
    @NotNull
    private BigDecimal indentPrice;

    //取货地址ID，订单类型非随意帮时必填
    private Integer takeGoodAddressId;

    //送达地点ID，订单类型为帮我递时必填
    private Integer shippingAddressId;

    //联系人姓名,不能用publisherId查，因为可能不同，订单类型非随意帮时必填
    private String publisherName;

    //快递公司名，仅订单类型为帮我递时非空
    private String companyName;

    //取货码，仅订单类型为帮我递时非空
    private String pickupCode;

    //物品金额，仅订单类型为帮我购时非空
    private BigDecimal goodPrice;

    //订单状态,待接单：WAIT_FOR_PERFORMER，执行中：PERFORMING，货物已送达：ARRIVED，已完成：COMPLETED
    @Enumerated(EnumType.STRING)
    private IndentState indentState;

    //创建时间
    @Column(updatable = false,insertable = false)
    @NotNull
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    //最近更新时间
    @Column(updatable = false,insertable = false)
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
}
