package com.wizzstudio.substitute.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wizzstudio.substitute.util.serializer.Date2LongSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 优惠券领取记录
 * Created By Cx On 2018/11/19 23:25
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class CouponRecord {

    //优惠券领取记录ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //优惠券ID
    private Integer couponId;

    //用户Id
    private String ownerId;

    //是否已使用，0：否，1：是,默认为0
    private Boolean isUsed;

    //失效时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date invalidTime;
}
