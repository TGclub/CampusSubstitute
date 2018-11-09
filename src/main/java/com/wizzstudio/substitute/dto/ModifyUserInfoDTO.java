package com.wizzstudio.substitute.dto;

import com.wizzstudio.substitute.enums.GenderEnum;

import java.io.Serializable;


public class ModifyUserInfoDTO implements Serializable {

    private static final long serialVersionUID = -6191983979387003051L;
    private String userName;
    private String trueName;
    private Long phoneNumber;
    private Integer school;
    private GenderEnum gender;


    public ModifyUserInfoDTO() {
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getSchool() {
        return school;
    }

    public void setSchool(Integer school) {
        this.school = school;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }
}
