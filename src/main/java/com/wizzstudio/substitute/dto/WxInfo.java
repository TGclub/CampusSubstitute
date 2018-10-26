package com.wizzstudio.substitute.dto;

import java.io.Serializable;

public class LoginDataDTO implements Serializable {

    private String code;

    private String encyptedData;

    private String iv;

    public LoginDataDTO() {
    }

    public LoginDataDTO(String code, String encyptedData, String iv) {
        this.code = code;
        this.encyptedData = encyptedData;
        this.iv = iv;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEncyptedData() {
        return encyptedData;
    }

    public void setEncyptedData(String encyptedData) {
        this.encyptedData = encyptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}
