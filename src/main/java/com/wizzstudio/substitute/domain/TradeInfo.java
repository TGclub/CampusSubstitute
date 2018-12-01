package com.wizzstudio.substitute.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wizzstudio.substitute.util.serializer.Date2LongSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 交易信息
 * Created By Cx On 2018/11/19 23:53
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class TradeInfo {
    //交易ID
    @Id
    private Integer tradeId;

    //优惠券ID
    private Integer couponId;

    //订单ID
    private Integer indentId;

    //订单悬赏金,单位元
    private BigDecimal indentPrice;

    //订单优惠额，单位元，默认0.00元
    private BigDecimal couponPrice;

    //实付金额,单位元
    private BigDecimal totalPrice;

    //创建时间
    @Column(updatable = false, insertable = false)
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    //最近更新时间
    @Column(updatable = false, insertable = false)
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
}
