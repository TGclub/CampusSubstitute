package com.wizzstudio.substitute.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wizzstudio.substitute.util.serializer.Date2LongSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券信息
 * Created By Cx On 2018/11/19 23:16
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@DynamicInsert
public class CouponInfo {
    //优惠券ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer couponId;

    //最小满减金额，单位元
    @Column
    private Integer leastPrice;

    //可减金额，单位元
    @Column
    private Integer reducePrice;

    //是否删除，0：否，1：是,默认0
    @Column
    private Boolean isDeleted;

    //validTime
    @Column
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date validTime;

    //失效时间
    @Column
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date invalidTime;

    //优惠券图片
    //该注解必须要，不然ddl-auto为validate时会报错，说数据库的blob是LongBinary，而byte[]是binary，
    // columnDefinition表示该字段在数据库中的实际类型
    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private byte[] picture;

    public CouponInfo() {
    }
}
