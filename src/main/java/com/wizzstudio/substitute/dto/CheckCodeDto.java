package com.wizzstudio.substitute.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 因为要序列化到redis中，所以需要实现序列化接口
 * Created By Cx On 2019/1/5 13:18
 */
@Data
@AllArgsConstructor
@Builder
public class CheckCodeDto implements Serializable {

    private static final long serialVersionUID = 2162315944809326971L;

    //电话
    private Long phone;

    //短信验证码
    private String code;

}
