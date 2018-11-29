package com.wizzstudio.substitute.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaTemplateService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.wizzstudio.substitute.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created By Cx On 2018/11/29 14:47
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    WxMaService wxMaService;

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
}
