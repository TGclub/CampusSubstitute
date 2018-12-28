package com.wizzstudio.substitute.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Created by Kikyou on 18-12-3
 */
@Data
public class AdminCouponDTO implements Serializable {

    //最小满减金额，单位元
    private Integer leastPrice;

    //可减金额，单位元
    private Integer reducePrice;

    //validTime
    private Long validTime;

    //失效时间
    private Long invalidTime;

    private MultipartFile picture;

    public AdminCouponDTO() {
    }
}
