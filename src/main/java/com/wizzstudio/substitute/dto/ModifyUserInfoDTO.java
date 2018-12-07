package com.wizzstudio.substitute.dto;

import com.wizzstudio.substitute.enums.GenderEnum;
import lombok.Data;

import java.io.Serializable;


@Data
public class ModifyUserInfoDTO implements Serializable {

    private static final long serialVersionUID = -6191983979387003051L;
    private String userName;
    private String trueName;
    private Long phoneNumber;
    private Integer school;
    private GenderEnum gender;


    public ModifyUserInfoDTO() {
    }

}
