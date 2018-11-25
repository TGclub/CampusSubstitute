package com.wizzstudio.substitute.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wizzstudio.substitute.util.serializer.Date2LongSerializer;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值信息
 * Created By Cx On 2018/11/21 20:56
 */
@Entity
@Data
@Builder
public class DepositInfo {
    //充值ID
    @Id
    private String depositId;

    //充值用户openid
    private String depositOpenid;

    //充值金额,单位：元
    private BigDecimal depositMoney;

    //充值是否成功，0：否，1：是，默认为0'
    private Boolean isSuccess;

    //创建时间
    @Column(updatable = false, insertable = false)
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    //最近更新时间
    @Column(updatable = false, insertable = false)
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
}
