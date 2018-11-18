package com.wizzstudio.substitute.domain;

import com.wizzstudio.substitute.enums.Role;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Kikyou on 18-11-18
 */
@Entity
@Data
public class AdminInfo {
    @Id
    private Integer adminId;
    @Column
    @NotNull
    @Size(min = 7, max = 11)
    private Long adminPhone;
    @Column
    @NotNull
    private String adminName;
    @Column
    @NotNull
    @Size(min = 8)
    private String adminPass;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role adminRole;
    @Column
    private Boolean isBoss;
    @Column
    private Integer adminSchoolId;

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

    private AdminInfo(Builder builder) {
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
        builder.adminPhone = copy.getAdminPhone();
        builder.adminName = copy.getAdminName();
        builder.adminPass = copy.getAdminPass();
        builder.adminRole = copy.getAdminRole();
        builder.isBoss = copy.getIsBoss();
        builder.adminSchoolId = copy.getAdminSchoolId();
        return builder;
    }


    public static final class Builder {
        private Long adminPhone;
        private String adminName;
        private String adminPass;
        private Role adminRole;
        private Boolean isBoss;
        private Integer adminSchoolId;

        private Builder() {
        }

        public Builder setAdminPhone(Long adminPhone) {
            this.adminPhone = adminPhone;
            return this;
        }

        public Builder setAdminName(String adminName) {
            this.adminName = adminName;
            return this;
        }

        public Builder setAdminPass(String adminPass) {
            this.adminPass = adminPass;
            return this;
        }

        public Builder setAdminRole(Role adminRole) {
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
