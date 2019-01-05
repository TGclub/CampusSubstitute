package com.wizzstudio.substitute.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created By Cx On 2019/1/5 16:57
 */
@Data
public class CheckCodeForm {
    @NotBlank
    String userId;
    @NotNull
    String phone;
}
