package com.wizzstudio.substitute.service;

/**
 * 发送模板消息
 * Created By Cx On 2018/11/29 14:46
 */
public interface PushMessageService {

    /**
     * 发送小程序模板消息: 使用WxMaService封装好的轮子
     */
    void sendTemplateMsg(String userOpenid, String formId);
}
