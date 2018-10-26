package com.wizzstudio.substitute.pojo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wizzstudio.substitute.enums.Gender;
import com.wizzstudio.substitute.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
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

    @Column
    private String trueName;
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
    @PositiveOrZero
    private Integer balance;

    /**
     * 累计收入
     */
    @Column
    @PositiveOrZero
    private Integer income;


    public User() {
    }

    private User(Builder builder) {
        setOpenid(builder.openid);
        setUserName(builder.userName);
        setPhoneNumber(builder.phoneNumber);
        setAvatar(builder.avatar);
        setSchool(builder.school);
        setGender(builder.gender);
        createTime = builder.createTime;
        updateTime = builder.updateTime;
        setRole(builder.role);
        setMasterId(builder.masterId);
        setBalance(builder.balance);
        setIncome(builder.income);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(User copy) {
        Builder builder = new Builder();
        builder.openid = copy.getOpenid();
        builder.userName = copy.getUserName();
        builder.phoneNumber = copy.getPhoneNumber();
        builder.avatar = copy.getAvatar();
        builder.school = copy.getSchool();
        builder.gender = copy.getGender();
        builder.createTime = copy.getCreateTime();
        builder.updateTime = copy.getUpdateTime();
        builder.role = copy.getRole();
        builder.masterId = copy.getMasterId();
        builder.balance = copy.getBalance();
        builder.income = copy.getIncome();
        return builder;
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


    public Date getCreateTime() {
        return createTime;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getUpdateTime() {
        return updateTime;
    }
    @PrePersist
    public void setCreateTime() {
        this.createTime = createTime;
    }

    @PreUpdate
    public void setUpdateTime() {
        this.updateTime = new Date();
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }
    public Integer getId() {
        return id;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public static final class Builder {
        private @NotNull String openid;
        private String userName;
        private @NotNull Integer phoneNumber;
        private String avatar;
        private @NotNull String school;
        private @NotNull Gender gender;
        private Date createTime;
        private Date updateTime;
        private @NotNull Role role;
        private Integer masterId;
        private @PositiveOrZero Integer balance;
        private @PositiveOrZero Integer income;

        private Builder() {
        }

        public Builder setOpenid(@NotNull String openid) {
            this.openid = openid;
            return this;
        }

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setPhoneNumber(@NotNull Integer phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public Builder setSchool(@NotNull String school) {
            this.school = school;
            return this;
        }

        public Builder setGender(@NotNull Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder setCreateTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public Builder setRole(@NotNull Role role) {
            this.role = role;
            return this;
        }

        public Builder setMasterId(Integer masterId) {
            this.masterId = masterId;
            return this;
        }

        public Builder setBalance(@PositiveOrZero Integer balance) {
            this.balance = balance;
            return this;
        }

        public Builder setIncome(@PositiveOrZero Integer income) {
            this.income = income;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
