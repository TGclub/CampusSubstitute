package com.wizzstudio.substitute.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Kikyou on 18-12-14
 */
@Data
public class UserCouponDTO implements Serializable {
    //最小满减金额，单位元
    private Integer leastPrice;

    //可减金额，单位元
    private Integer reducePrice;

    //validTime
    private Long validTime;

    //失效时间
    private Long invalidTime;

    private String pictureLink;

    public UserCouponDTO() {
    }
}
