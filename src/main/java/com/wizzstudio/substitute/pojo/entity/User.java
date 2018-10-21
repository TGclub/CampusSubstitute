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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    @NotNull
    private String open_id;

    @Column
    private String userName;

    @Column(length = 11, unique = true)
    @NotNull
    private Integer phoneNumber;

    @Column
    private String headPortrait;

    @Column
    @NotNull
    private String school;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    @Column
    private Date createTime;

    @Column
    private Date updateTime;

    /**
     * 身份证号
     */
    @Column(length = 18, unique = true)
    @NotNull
    private String idNumber;

    @Column
    private String email;

    @Column(length = 50)
    private String password;

    /**
     * 常用地址
     */
    @Column
    private String normalAddress;
    /**
     * 用户级别
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Role role;


    @Column
    private Integer masterId;

    /**
     * 是否使用过优惠券
     */
    @Column
    private Boolean isUsedDiscountCoupon;


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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getNormalAddress() {
        return normalAddress;
    }

    public void setNormalAddress(String normalAddress) {
        this.normalAddress = normalAddress;
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

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public boolean isUsedDiscountCoupon() {
        return isUsedDiscountCoupon;
    }

    public void setUsedDiscountCoupon(boolean usedDiscountCoupon) {
        isUsedDiscountCoupon = usedDiscountCoupon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
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

    public Boolean getUsedDiscountCoupon() {
        return isUsedDiscountCoupon;
    }

    public void setUsedDiscountCoupon(Boolean usedDiscountCoupon) {
        isUsedDiscountCoupon = usedDiscountCoupon;
    }
}
