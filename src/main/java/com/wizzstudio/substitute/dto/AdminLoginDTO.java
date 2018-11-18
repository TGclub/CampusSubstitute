package com.wizzstudio.substitute.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Kikyou on 18-11-18
 */
@Data
public class AdminLoginDTO implements Serializable {
    private String adminName;
    private String password;

    public AdminLoginDTO() {
    }

    public AdminLoginDTO(String adminName, String password) {

        this.adminName = adminName;
        this.password = password;
    }
}
