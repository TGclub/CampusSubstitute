package com.wizzstudio.substitute.pojo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wizzstudio.substitute.enums.Gender;
import com.wizzstudio.substitute.enums.Role;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "user")
@Data
public class User implements Serializable {

    //用户Id,由26位大小写字母+10位数组随机生成
    @Id
    private String id;

    //用户微信openid
    @NotNull
    private String openid;

    //师傅Id
    private Integer masterId;

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
    private String school;

    //用户性别，男：”MALE”,女：”FAMALE”,未知：”NO_LIMITED”
    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    /**
     * 用户级别: 普通用户：”ROLE_USER”,一级管理员:”ROLE_ADMIN_1” ,二级管理员:”ROLE_ADMIN_2”
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Role role;

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

    @Column(insertable = false,updatable = false)
    private Date createTime;

    @Column(updatable = false,insertable = false)
    private Date updateTime;

}
