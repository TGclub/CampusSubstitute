package com.wizzstudio.substitute.dto;

import com.wizzstudio.substitute.enums.Gender;

import java.io.Serializable;

public class ApprenticeBasicInfo implements Serializable {

    private static final long serialVersionUID = -179070031366192478L;

    private String id;

    private String userName;

    private String avatar;

    private String school;

    private Gender gender;

    public ApprenticeBasicInfo() {
    }


    public ApprenticeBasicInfo(String id, String userName, String avatar, String school, Gender gender) {
        this.id = id;
        this.userName = userName;
        this.avatar = avatar;
        this.school = school;
        this.gender = gender;
    }

    private ApprenticeBasicInfo(Builder builder) {
        setId(builder.id);
        setUserName(builder.userName);
        setAvatar(builder.avatar);
        setSchool(builder.school);
        setGender(builder.gender);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(ApprenticeBasicInfo copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.userName = copy.getUserName();
        builder.avatar = copy.getAvatar();
        builder.school = copy.getSchool();
        builder.gender = copy.getGender();
        return builder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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


    public static final class Builder {
        private String id;
        private String userName;
        private String avatar;
        private String school;
        private Gender gender;

        private Builder() {
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setAvatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public Builder setSchool(String school) {
            this.school = school;
            return this;
        }

        public Builder setGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public ApprenticeBasicInfo build() {
            return new ApprenticeBasicInfo(this);
        }
    }
}
