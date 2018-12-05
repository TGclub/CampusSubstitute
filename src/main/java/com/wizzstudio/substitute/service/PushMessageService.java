package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.domain.Indent;

/**
 * 发送模板消息
 * Created By Cx On 2018/11/29 14:46
 */
public interface PushMessageService {

    /**
     * 发送小程序模板消息: 使用WxMaService封装好的轮子
     */
    void sendTemplateMsg(String userOpenid, String formId);

    /**
     * 发送短信给该区域的负责人
     */
    void sendPhoneMsg2Boss(int schoolId, int msgType);
}
