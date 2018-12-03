package com.wizzstudio.substitute.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wizzstudio.substitute.util.serializer.Date2LongSerializer;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kikyou on 18-12-3
 */
@Data
public class CouponDTO implements Serializable
{
    private Integer couponId;

    //最小满减金额，单位元
    private Integer leastPrice;

    //可减金额，单位元
    private Integer reducePrice;
    
    //validTime
    private Long validTime;

    //失效时间
    private Long invalidTime;

    private MultipartFile picture;

    public CouponDTO() {
    }
}
