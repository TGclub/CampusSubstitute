package com.wizzstudio.substitute.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 配置表
 * Created By Cx On 2019/1/24 9:52
 */
@Data
@Entity
@DynamicUpdate
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //公司分成占比
    private BigDecimal companyRatio;

    //推荐人分成占比
    private BigDecimal masterRatio;

    //接单人分成占比
    private BigDecimal performerRatio;

    //最低单价
    private Integer leastPrice;

    //超时时间，单位：s
    private Integer overTime;
}
