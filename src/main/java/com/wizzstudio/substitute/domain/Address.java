package com.wizzstudio.substitute.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 收获地址表
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@DynamicInsert
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 收货人电话
     */
    private Long phone;

    /**
     * 收货人姓名
     */
    private String userName;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 常用收货地址对应的用户Id
     **/
    private String userId;

    /**
     * 是否已删除，0：否，1：是，默认0
     */
    private Boolean isDeleted;

    public Address(String address, String userId) {
        this.address = address;
        this.userId = userId;
    }

    public Address() {
    }
}
