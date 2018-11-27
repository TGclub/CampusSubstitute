package com.wizzstudio.substitute.domain;

import com.wizzstudio.substitute.enums.Role;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Created by Kikyou on 18-11-18
 */
@Entity
@Data
public class AdminInfo {
    //管理员Id
    @Id
    private Integer adminId;
    //管理员电话
    @Column
    @NotNull
    private Long adminPhone;
    //管理员账号，唯一
    @Column
    @NotNull
    private String adminName;
    //管理员密码
    @Column
    @NotNull
    //@Size(min = 8)
    private String adminPass;
    //管理员角色,一级管理员：ROLE_ADMIN_1，二级管理员：ROLE_ADMIN_2
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role adminRole;
    //是否为区域负责人,0:否，1：是
    private Boolean isBoss;
    //管理员学校Id ，一级管理员没有该属性
    @Column
    private Integer adminSchoolId;

    private AdminInfo(Builder builder) {
        setAdminId(builder.adminId);
        setAdminPhone(builder.adminPhone);
        setAdminName(builder.adminName);
        setAdminPass(builder.adminPass);
        setAdminRole(builder.adminRole);
        setIsBoss(builder.isBoss);
        setAdminSchoolId(builder.adminSchoolId);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(AdminInfo copy) {
        Builder builder = new Builder();
        builder.adminId = copy.getAdminId();
        builder.adminPhone = copy.getAdminPhone();
        builder.adminName = copy.getAdminName();
        builder.adminPass = copy.getAdminPass();
        builder.adminRole = copy.getAdminRole();
        builder.isBoss = copy.getIsBoss();
        builder.adminSchoolId = copy.getAdminSchoolId();
        return builder;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public AdminInfo(Integer adminId, Long adminPhone, String adminName, String adminPass, Role adminRole, Boolean isBoss, Integer adminSchoolId) {
        this.adminId = adminId;
        this.adminPhone = adminPhone;
        this.adminName = adminName;
        this.adminPass = adminPass;
        this.adminRole = adminRole;
        this.isBoss = isBoss;
        this.adminSchoolId = adminSchoolId;
    }

    public AdminInfo() {
    }


    public static final class Builder {
        private Integer adminId;
        private @NotNull @Size(min = 7, max = 11) Long adminPhone;
        private @NotNull String adminName;
        private @NotNull @Size(min = 8) String adminPass;
        private @NotNull Role adminRole;
        private Boolean isBoss;
        private Integer adminSchoolId;

        private Builder() {
        }

        public Builder setAdminId(Integer adminId) {
            this.adminId = adminId;
            return this;
        }

        public Builder setAdminPhone(@NotNull @Size(min = 7, max = 11) Long adminPhone) {
            this.adminPhone = adminPhone;
            return this;
        }

        public Builder setAdminName(@NotNull String adminName) {
            this.adminName = adminName;
            return this;
        }

        public Builder setAdminPass(@NotNull @Size(min = 8) String adminPass) {
            this.adminPass = adminPass;
            return this;
        }

        public Builder setAdminRole(@NotNull Role adminRole) {
            this.adminRole = adminRole;
            return this;
        }

        public Builder setIsBoss(Boolean isBoss) {
            this.isBoss = isBoss;
            return this;
        }

        public Builder setAdminSchoolId(Integer adminSchoolId) {
            this.adminSchoolId = adminSchoolId;
            return this;
        }

        public AdminInfo build() {
            return new AdminInfo(this);
        }
    }
}
