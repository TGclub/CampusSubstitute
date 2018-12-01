package com.wizzstudio.substitute.VO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wizzstudio.substitute.enums.GenderEnum;
import com.wizzstudio.substitute.enums.indent.IndentStateEnum;
import com.wizzstudio.substitute.enums.indent.IndentTypeEnum;
import com.wizzstudio.substitute.util.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用于返回给前端的订单信息类
 * Created By Cx On 2018/11/27 11:22
 */
@Data
public class IndentVO {
    //订单id
    private Integer indentId;

    //接单人人id
    private String performerId;

    //下单用户id
    private String publisherId;

    //联系人电话,不能用publisherId查，因为可能不同
    private Long publisherPhone;

    //订单类型，帮我购：HELP_BUY，帮我递：HELP_SEND，随意帮：HELP_OTHER
    private IndentTypeEnum indentType;

    //订单要求性别，男：”MALE”,女：”FEMALE”,不限：”NO_LIMITED”
    private GenderEnum requireGender;

    //任务内容
    private String indentContent;

    //订单悬赏金
    private BigDecimal indentPrice;

    //加急类型，0:不加急，1:超时 2:退单
    private Integer urgentType;

    //加急订单是否已处理，0：否，1：是，默认为0
    private Boolean isSolved;

    //订单状态,待接单：WAIT_FOR_PERFORMER，执行中：PERFORMING，货物已送达：ARRIVED，已完成：COMPLETED
    private IndentStateEnum indentState;

    //取货地址，订单类型非随意帮时必填
    private String takeGoodAddress;

    //联系人姓名,不能用publisherId查，因为可能不同，订单类型非随意帮时必填
    private String publisherName;

    //快递公司名，仅订单类型为帮我递时非空
    private String companyName;

    //取货码，仅订单类型为帮我递时非空
    private String pickupCode;

    //物品金额，仅订单类型为帮我购时非空
    private BigDecimal goodPrice;

//======================================需要自己拼接的字段========================================================
    //送达地点，订单类型为帮我递时必填
    private String shippingAddress;

    //下单人性别，男：”MALE”,女：”FEMALE”,未知：”NO_LIMITED”
    private GenderEnum publisherGender;

    //下单人学校名称
    private String publisherSchool;

    //下单人头像URL
    private String publisherAvatar;

    //下单人微信昵称
    private String publisherNickName;

    //接单人性别，男：”MALE”,女：”FEMALE”,未知：”NO_LIMITED”
    private GenderEnum performerGender;

    //接单人学校名称
    private String performerSchool;

    //接单人头像URL
    private String performerAvatar;

    //接单人微信昵称
    private String performerNickName;

    //创建时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    //最近更新时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
}
