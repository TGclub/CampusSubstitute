package com.wizzstudio.substitute.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Kikyou on 18-12-10
 */
@Data
public class AddressDTO implements Serializable {

    private Integer addressId;

    private String address;

    private Long phone;

    private String userName;

    private String userId;
}
