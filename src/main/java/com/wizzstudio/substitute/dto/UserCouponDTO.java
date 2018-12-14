package com.wizzstudio.substitute.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kikyou on 18-12-14
 */
@Data
public class UserCouponDTO implements Serializable {

    private Integer couponId;
    //最小满减金额，单位元
    private Integer leastPrice;

    //可减金额，单位元
    private Integer reducePrice;

    //validTime
    private Date validTime;

    //失效时间
    private Date invalidTime;

    private String pictureLink;

    public UserCouponDTO() {
    }
}
