package com.wizzstudio.substitute;

import com.alibaba.fastjson.JSONObject;
import com.wizzstudio.substitute.constants.Constant;
import okhttp3.*;

import java.io.IOException;

/**
 * Created by Kikyou on 18-11-8
 */
public class ControllerTestUtil {

    public static final String BASE_URL = Constant.TEST_HOST + ":" + Constant.TEST_PORT;

    private static final MediaType JSON_TYPE
            = MediaType.parse("application/json; charset=utf-8");

    static OkHttpClient client = new OkHttpClient();



    public static void sendGetRequest(String url) {
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .build();
        execute(request);
    }

    public static void sendPostRequest(MediaType type, Object obj, String url) {
        RequestBody body = RequestBody.create(type, JSONObject.toJSONString(obj));
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .post(body)
                .build();
        execute(request);

    }

    public static void execute(Request request) {
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.code());
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
