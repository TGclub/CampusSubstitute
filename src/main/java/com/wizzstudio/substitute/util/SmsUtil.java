package com.wizzstudio.substitute.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.wizzstudio.substitute.config.AliSmsConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * todo 怎样确保不被恶意请求调用
 * 阿里云短信服务工具类
 * SMS short message service
 * Created By Cx On 2018/11/13 0:02
 */
@Slf4j
public class SmsUtil {

    //产品名称:云通信短信API产品,开发者无需替换
    private static final String PRODUCT = "Dysmsapi";
    //产品域名,开发者无需替换
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    public static void sendSms(SendSmsRequest request, AliSmsConfig aliSmsConfig) throws ClientException {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂只支持该区域，请勿修改
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                aliSmsConfig.getAccessKeyId(), aliSmsConfig.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //发送短信
        SendSmsResponse response = acsClient.getAcsResponse(request);
        log.info("【发送短信】电话 = {}，发送状态 = {}", request.getPhoneNumbers(), response.getCode());
    }
}
