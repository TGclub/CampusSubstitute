package com.wizzstudio.substitute.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 统计信息
 * Created By Cx On 2018/11/19 22:50
 */
@Builder
@Data
@Entity
public class CountInfo {
    //统计ID
    @Id
    private Integer countId;

    //学校id
    private Integer schoolId;

    //统计时间，格式：yyyyMMdd,如：20181115
    private Integer countDate;

    //当日下单数量
    private Integer newIndent;

    //当日完成订单数量,默认为0
    private Integer finishedIndent;

    //当日收益额，单位：元，默认为0.00
    private BigDecimal income;

    //当日登录量，不去重,默认为0(todo 先不做)
    private Integer loginUser;
}
