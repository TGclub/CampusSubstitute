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
import java.util.Date;

/**
 * 提现请求
 * Created By Cx On 2018/11/20 0:02
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class WithdrawRequest {
    //提现请求id
    @Id
    private Integer withdrawId;

    //用户id
    private String userId;

    //是否已处理，0：未处理，1：已处理，默认为0
    private Boolean isSolved;

    //创建时间，单位：毫秒，默认为插入时间
    @Column(updatable = false, insertable = false)
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
}
