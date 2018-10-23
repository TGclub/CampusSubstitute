package com.wizzstudio.substitute.pojo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wizzstudio.substitute.enums.Gender;
import com.wizzstudio.substitute.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "user")

public class User implements Serializable {

    //用户Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //用户微信openid
    @Column
    @NotNull
    private String openid;

    //用户名
    @Column
    private String userName;

    //电话号码
    @Column(length = 11, unique = true)
    @NotNull
    private Integer phoneNumber;

    //用户头像url
    @Column
    private String avatar;

    //学校
    @Column
    @NotNull
    private String school;

    //性别
    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    @Column
    private Date createTime;

    @Column
    private Date updateTime;


    /**
     * 用户级别
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Role role;

    //师傅Id
    @Column
    private Integer masterId;

    /**
     * 余额
     */
    @Column
    private Integer balance;

    /**
     * 累计收入
     */
    @Column
    private Integer income;


    public User() {
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getCreateTime() {
        return createTime;
    }

    @PrePersist
    public void setCreateTime() {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    @PreUpdate
    public void setUpdateTime() {
        this.updateTime = new Date();
    }

    public Integer getId() {
        return id;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

}
