package com.wizzstudio.substitute.dto;

import com.wizzstudio.substitute.enums.Gender;

import java.io.Serializable;

public class ApprenticeBasicInfo implements Serializable {

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
}
