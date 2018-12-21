package com.wizzstudio.substitute.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.wizzstudio.substitute.config.AliSmsConfig;
import com.wizzstudio.substitute.domain.AdminInfo;
import com.wizzstudio.substitute.domain.Indent;
import com.wizzstudio.substitute.domain.User;
import com.wizzstudio.substitute.enums.Role;
import com.wizzstudio.substitute.enums.indent.IndentStateEnum;
import com.wizzstudio.substitute.enums.indent.UrgentTypeEnum;
import com.wizzstudio.substitute.service.AdminService;
import com.wizzstudio.substitute.service.PushMessageService;
import com.wizzstudio.substitute.service.UserService;
import com.wizzstudio.substitute.util.CommonUtil;
import com.wizzstudio.substitute.util.SmsUtil;
import com.wizzstudio.substitute.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.wizzstudio.substitute.enums.indent.UrgentTypeEnum.OVERTIME;

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
    @Autowired
    UserService userService;

    @Override
    public void sendTemplateMsg(Indent indent, String formId){
        IndentStateEnum indentState = indent.getIndentState();
        List<String> params = new ArrayList<>();
        String userId,templateId,date = TimeUtil.getFormatTime(new Date(),"yyyy-MM-dd HH:mm:ss");
        switch (indentState){
            //待接单，说明下单人被取消订单
            case WAIT_FOR_PERFORMER:
                params.add("您的订单被退单惹(>﹏<)");
                params.add("待接单");
                params.add(date);
                params.add("很多人都是你生命中的过客，总有一个人在等你");
                userId = indent.getPublisherId();
                templateId = "-_wlctvQjdRVNM-oBjQuSSPuEVJXQ9YeSJ86MksxUdo";
                break;
            //已送达，给下单人发消息
            case ARRIVED:
                params.add("您的订单已经送达");
                params.add("订单已送达");
                params.add(date);
                params.add("帮我看看来的是帅气的小哥哥还是漂亮的小姐姐～(￣▽￣～)~");
                userId = indent.getPublisherId();
                templateId = "t0bQ-DRbY1JcGiz0IwiulOP6hP_GGPuuVQ64D56hDqg";
                break;
            //订单取消，说明接单人被取消订单
            case CANCELED:
                params.add("您的订单被退单惹(>﹏<)");
                params.add("订单已取消");
                params.add(date);
                params.add("很多人都是你生命中的过客，总有一个人在等你");
                userId = indent.getPerformerId();
                templateId = "-_wlctvQjdRVNM-oBjQuSSPuEVJXQ9YeSJ86MksxUdo";
                break;
            //完成订单，给接单人发消息
            case COMPLETED:
                params.add("您的任务已经确认完成");
                params.add("订单已完成");
                params.add(date);
                params.add("您刚刚不只是完成了一个订单，更是开始了一段缘份(｡･ω･｡)ﾉ♡");
                userId = indent.getPerformerId();
                templateId = "2dOr4-NZv7nJ8ps2hcUhAmJx-vpVipuaTQrQ8CFk3eU";
                break;
            case PERFORMING:
                params.add("您的任务已经被接取喽～(≧∇≦)/");
                params.add("订单已被接");
                params.add(date);
                params.add("您的订单正在奔向您～");
                userId = indent.getPublisherId();
                templateId = "eAJcNvlLEMQ2YNlWOTCC3KACAoFGYpGF0Jh5kSosqwE";
                break;
            default:
                log.error("[微信消息推送]发送失败，订单状态有误，indent={}",indent);
                return;
        }
        try {
            String openid = userService.findUserById(userId).getOpenid();
            sendTemplateMsg(openid,formId,templateId,params);
        } catch (WxErrorException e) {
            log.error("[微信消息推送]推送失败，e={}",e);
        }catch (Exception ignored){}
    }

    @Override
    public void sendPhoneMsg2User(Indent indent) {
        try {
            IndentStateEnum indentState = indent.getIndentState();
            String userId1, userId2,phone,templateCode;
            String date = TimeUtil.getFormatTime(indent.getCreateTime(),"MM月dd日 HH:mm");
            List<String> params = new ArrayList<>();
            //todo templateCode
            switch (indentState){
                //待接单，说明下单人被取消订单
                case WAIT_FOR_PERFORMER:
                    userId1 = indent.getPublisherId();
                    userId2 = indent.getPerformerId();
                    templateCode = "-_wlctvQjdRVNM-oBjQuSSPuEVJXQ9YeSJ86MksxUdo";
                    break;
                //订单取消，说明接单人被取消订单
                case CANCELED:
                    userId1 = indent.getPerformerId();
                    userId2 = indent.getPublisherId();
                    templateCode = "-_wlctvQjdRVNM-oBjQuSSPuEVJXQ9YeSJ86MksxUdo";
                    break;
                //订单被接，告诉下单人
                case PERFORMING:
                    userId1 = indent.getPublisherId();
                    userId2 = indent.getPerformerId();
                    templateCode = "eAJcNvlLEMQ2YNlWOTCC3KACAoFGYpGF0Jh5kSosqwE";
                    break;
                default:
                    log.error("[微信消息推送]发送失败，订单状态有误，indent={}",indent);
                    return;
            }
            User user = userService.findUserById(userId1);
            phone = String.valueOf(user.getPhone());
            params.add(user.getUserName());
            params.add(date);
            params.add(userService.findUserById(userId2).getUserName());
            sendMsg(templateCode,phone,params);
        } catch (ClientException e) {
            log.error("[发送短信]出错了，e={}",e);
        } catch (Exception ignored){}
    }

    @Override
    public void sendPhoneMsg2Admin(Indent indent) {
        try {
            String phone = "",templateCode;
            //发送短信给所有该区域的二级管理员
            User user = userService.findUserById(indent.getPublisherId());
            List<AdminInfo> adminInfos = adminService.getAllAdminInfoBySchoolIdAndRole(user.getSchoolId(), Role.ROLE_ADMIN_2);
            for (AdminInfo adminInfo : adminInfos){
                phone += adminInfo.getAdminPhone() + ",";
            }
            phone = phone.substring(0,phone.length()-1);
            String date = TimeUtil.getFormatTime(indent.getCreateTime(),"MM月dd日 HH:mm");
            List<String> params = new ArrayList<>();
            params.add(user.getUserName());
            params.add(date);
            UrgentTypeEnum urgentType = CommonUtil.getEnum(indent.getUrgentType(),UrgentTypeEnum.class);
            if (urgentType == null){
                log.error("[发送短信]出错了，urgentType={}",indent.getUrgentType());
                return;
            }
            //todo templateCode
            switch (urgentType){
                //超时
                case OVERTIME:
                    templateCode = "x";
                    break;
                //退单
                case CANCEL:
                    templateCode = "x";
                    break;
                default:
                    log.error("[发送短信]出错了，urgentType={}",urgentType);
                    return;
            }
            sendMsg(templateCode,phone,params);
        } catch (ClientException e) {
            log.error("[发送短信]出错了，e={}",e);
        } catch (Exception ignored){}
    }


    /**
     * 发送指定微信模板消息给指定用户
     * @param openid 用户openid
     * @param formId 表单提交id
     * @param templateId 模板消息id
     * @param paramList 参数列表（"需符合第N个参数的key叫keywordN"）
     */
    private void sendTemplateMsg(String openid,String formId,String templateId,List<String> paramList) throws WxErrorException {
        WxMaMsgService msgService = wxMaService.getMsgService();
        WxMaTemplateMessage templateMessage = new WxMaTemplateMessage();
        templateMessage.setToUser(openid);
        templateMessage.setFormId(formId);
        templateMessage.setTemplateId(templateId);
        for (int i = 0; i < paramList.size(); i++){
            String key = "keyword"+(i+1);
            templateMessage.addData(new WxMaTemplateMessage.Data(key,paramList.get(i)));
        }
        msgService.sendTemplateMsg(templateMessage);
    }

    /**
     * 给指定电话用户发送指定模板消息
     * @param templateCode 短信模板ID
     * @param phone 发送用户电话，若有多个用户，用逗号隔开，如："1234,2234"
     * @param paramList 短信模板参数列表(注意，模板消息设置时，变量要用{param1}、{param2}……{paramN})
     */
    private void sendMsg(String templateCode, String phone,List<String> paramList) throws ClientException {
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:短信签名名称-可在短信控制台中找到
        request.setSignName("bangbang");
        //必填:短信模板ID-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${param1},您的验证码为${param2}"时,此处的值为
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < paramList.size(); i++){
            params.put("param"+(i+1), paramList.get(i));
        }
        request.setTemplateParam(JSON.toJSON(params).toString());
        //必填:,短信接收号码,支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码
        request.setPhoneNumbers(phone);
        SmsUtil.sendSms(request, aliSmsConfig);
    }
}
