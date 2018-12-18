package com.wizzstudio.substitute.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 订单、用户id
 * Created By Cx On 2018/11/26 17:12
 */
@Data
public class IndentUserForm {
    //订单id
    @NotNull(message = "订单id不能为空")
    private Integer indentId;

    //用户id
    @NotBlank(message = "用户id不能为空")
    private String userId;

    private String formId;
}
