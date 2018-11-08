package com.wizzstudio.substitute.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.wizzstudio.substitute.ControllerTestUtil;
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

    /**
     * 此处和showdoc中有两点不一致，一是school此处返回为schoolId
     *                          二是 phoneNumber 此处返回为phone 其余一致
     */
    @Test
    @Rollback(false)
    void getUseInfo() {

        ControllerTestUtil.sendGetRequest("/user/rEEFEE");
    }

    /**
     * 测试通过
     */
    @Test
    void modifyUserInfo() {
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
            System.out.println(response.body().string());
            System.out.println(response.code());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAllApprenticesInfo() {
    }

    @Test
    void getMasterInfo() {
    }

    /**
     * 测试通过
     */
    @Test
    void addMaster() {
        ControllerTestUtil.sendPostRequest(JSON_TYPE, null, "/user/master/rEEFEE/EEETEE");
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