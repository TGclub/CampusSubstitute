package com.wizzstudio.substitute.VO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wizzstudio.substitute.util.serializer.Date2LongSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Kikyou on 18-12-7
 */
@Data
public class WithdrawRequestVO implements Serializable {

    private Integer withdrawId;

    //用户id
    private String userId;

    //是否已处理，0：未处理，1：已处理，默认为0
    private Boolean isSolved;

    //创建时间，单位：毫秒，默认为插入时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    private Long phone;

    private BigDecimal balance;

    private String schoolName;

    private String userName;
}
