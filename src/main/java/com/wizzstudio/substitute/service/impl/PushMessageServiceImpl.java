package com.wizzstudio.substitute.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaTemplateService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.wizzstudio.substitute.config.AliSmsConfig;
import com.wizzstudio.substitute.domain.AdminInfo;
import com.wizzstudio.substitute.domain.Indent;
import com.wizzstudio.substitute.service.AdminService;
import com.wizzstudio.substitute.service.PushMessageService;
import com.wizzstudio.substitute.util.ResultUtil;
import com.wizzstudio.substitute.util.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created By Cx On 2018/11/29 14:47
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    WxMaService wxMaService;
    @Autowired
    AdminService adminService;
    @Autowired
    AliSmsConfig aliSmsConfig;

    //todo 未完成
    @Override
    public void sendTemplateMsg(String userOpenid, String formId) {
        WxMaMsgService msgService = wxMaService.getMsgService();
        WxMaTemplateMessage templateMessage = new WxMaTemplateMessage();
        templateMessage.setToUser(userOpenid);
        templateMessage.setFormId(formId);
//        templateMessage.setTemplateId();
//        templateMessage.addData();
        try {
            msgService.sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("[微信消息推送]推送失败，e={}",e);
        }
    }

    //todo 未完成
    @Override
    public void sendPhoneMsg2Boss(int schoolId, int msgType) {
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:短信签名名称-可在短信控制台中找到
        request.setSignName("testCx");
        //必填:短信模板ID-可在短信控制台中找到
        request.setTemplateCode("SMS_150865237");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${param1},您的验证码为${param2}"时,此处的值为
        Map<String, String> params = new HashMap<>();
        params.put("param1", "test1");
        params.put("param2", "test2");
        request.setTemplateParam(JSON.toJSON(params).toString());
        //添加发送对象电话
        List<AdminInfo> adminInfos = adminService.findAllBossBySchoolId(schoolId);
        StringBuilder sb = new StringBuilder();
        adminInfos.forEach(x -> {
            sb.append(x.getAdminPhone()).append(",");
        });
        //必填:,短信接收号码,支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码
        request.setPhoneNumbers(sb.toString().substring(0,sb.toString().length()-1));
        try {
            SmsUtil.sendSms(request, aliSmsConfig);
        } catch (ClientException e) {
            log.error("出错了");
        }
    }
}
