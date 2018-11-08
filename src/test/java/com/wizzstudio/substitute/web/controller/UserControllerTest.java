package com.wizzstudio.substitute.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.wizzstudio.substitute.constants.Constant;
import com.wizzstudio.substitute.dto.ModifyUserInfoDTO;
import com.wizzstudio.substitute.dto.UserBasicInfo;
import com.wizzstudio.substitute.enums.Gender;
import com.wizzstudio.substitute.util.RandomUtil;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Kikyou on 18-11-8
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class UserControllerTest {

    OkHttpClient client = new OkHttpClient();

    private static final MediaType JSON_TYPE
            = MediaType.parse("application/json; charset=utf-8");

    @Autowired
    private UserController userController;

    @Test
    @Rollback(false)
    void getUseInfo() {
        ModifyUserInfoDTO basicInfo = new ModifyUserInfoDTO();
        basicInfo.setTrueName("TEST");
        basicInfo.setGender(Gender.MALE);
        basicInfo.setPhoneNumber(12345L);
        basicInfo.setSchool(1);
        basicInfo.setUserName("TEST");
        RequestBody body = RequestBody.create(JSON_TYPE, JSONObject.toJSONString(basicInfo));
        Request request = new Request.Builder()
                .url(Constant.TEST_HOST + ":" + Constant.TEST_PORT + "/user/info/" + "rEEFEE")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.message());
            System.out.println(response.code());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void modifyUserInfo() {
    }

    @Test
    void getAllApprenticesInfo() {
    }

    @Test
    void getMasterInfo() {
    }

    @Test
    void addMaster() {
    }

    @Test
    void addAddress() {
    }

    @Test
    void getSchool() {
    }

    @Test
    void getAllAddress() {
    }
}