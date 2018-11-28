package com.wizzstudio.substitute.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wizzstudio.substitute.enums.GenderEnum;
import com.wizzstudio.substitute.util.serializer.Date2LongSerializer;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NamedQuery(name = "getAllApprentice", query = "select u from User u where" +
        " u.masterId = :account")
public class User implements Serializable {

    private static final long serialVersionUID = 3537218534766243734L;

    //用户Id,由26位大小写字母+10位数组随机生成
    @Id
    private String id;

    //用户微信openid
    @NotNull
    private String openid;

    //师傅Id
    private String masterId;

    //用户昵称，微信名
    @NotNull
    private String userName;

    //用户真实姓名
    private String trueName;

    //电话号码
    private Long phone;

    //用户头像url
    @NotNull
    private String avatar;

    //学校
    private Integer schoolId;

    //用户性别，男：”MALE”,女：”FEMALE”,未知：”NO_LIMITED”
    @Enumerated(EnumType.STRING)
    @NotNull
    private GenderEnum gender;

    /**
     * 余额
     */
    @NotNull
    private BigDecimal balance;

    /**
     * 累计收入
     */
    @NotNull
    private BigDecimal allIncome;

    //师傅收入：当推荐人获得的收入
    private BigDecimal masterIncome;

    @Column(insertable = false, updatable = false)
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @Column(updatable = false, insertable = false)
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    public User() {
    }

    private User(Builder builder) {
        setId(builder.id);
        setOpenid(builder.openid);
        setMasterId(builder.masterId);
        setUserName(builder.userName);
        setTrueName(builder.trueName);
        setPhone(builder.phone);
        setAvatar(builder.avatar);
        setSchoolId(builder.school);
        setGender(builder.gender);
        setBalance(builder.balance);
        setAllIncome(builder.allIncome);
        setMasterIncome(builder.masterIncome);
        setCreateTime(builder.createTime);
        setUpdateTime(builder.updateTime);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(User copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.openid = copy.getOpenid();
        builder.masterId = copy.getMasterId();
        builder.userName = copy.getUserName();
        builder.trueName = copy.getTrueName();
        builder.phone = copy.getPhone();
        builder.avatar = copy.getAvatar();
        builder.school = copy.getSchoolId();
        builder.gender = copy.getGender();
        builder.balance = copy.getBalance();
        builder.allIncome = copy.getAllIncome();
        builder.masterIncome = copy.getMasterIncome();
        builder.createTime = copy.getCreateTime();
        builder.updateTime = copy.getUpdateTime();
        return builder;
    }


    public static final class Builder {
        private String id;
        private @NotNull String openid;
        private String masterId;
        private @NotNull String userName;
        private String trueName;
        private Long phone;
        private @NotNull String avatar;
        private Integer school;
        private @NotNull GenderEnum gender;
        private @NotNull BigDecimal balance;
        private @NotNull BigDecimal allIncome;
        private BigDecimal masterIncome;
        private Date createTime;
        private Date updateTime;

        private Builder() {
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setOpenid(@NotNull String openid) {
            this.openid = openid;
            return this;
        }

        public Builder setMasterId(String masterId) {
            this.masterId = masterId;
            return this;
        }

        public Builder setUserName(@NotNull String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setTrueName(String trueName) {
            this.trueName = trueName;
            return this;
        }

        public Builder setPhone(Long phone) {
            this.phone = phone;
            return this;
        }

        public Builder setAvatar(@NotNull String avatar) {
            this.avatar = avatar;
            return this;
        }

        public Builder setSchool(Integer school) {
            this.school = school;
            return this;
        }

        public Builder setGender(@NotNull GenderEnum gender) {
            this.gender = gender;
            return this;
        }

        public Builder setBalance(@NotNull BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Builder setAllIncome(@NotNull BigDecimal allIncome) {
            this.allIncome = allIncome;
            return this;
        }

        public Builder setMasterIncome(BigDecimal masterIncome) {
            this.masterIncome = masterIncome;
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

        public User build() {
            return new User(this);
        }
    }
}
