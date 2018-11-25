package com.wizzstudio.substitute.controller;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.wizzstudio.substitute.config.AliSmsConfig;
import com.wizzstudio.substitute.domain.Indent;
import com.wizzstudio.substitute.dto.wx.WxPrePayDto;
import com.wizzstudio.substitute.enums.ResultEnum;
import com.wizzstudio.substitute.exception.SubstituteException;
import com.wizzstudio.substitute.form.PayForm;
import com.wizzstudio.substitute.service.WxPayService;
import com.wizzstudio.substitute.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created By Cx On 2018/11/6 22:09
 */
@RestController
@Slf4j
@RequestMapping("/wxpay")
public class WxPayController {

    @Autowired
    WxPayService wxPayService;
    @Autowired
    AliSmsConfig aliSmsConfig;

    private WxPrePayDto createWxPrePayDto(PayForm payForm, HttpServletRequest request) {
        int totalFee = MoneyUtil.Yuan2Fen(payForm.getTotalFee());
        return WxPrePayDto.builder().indentId(RandomUtil.genUniqueKey())
                .openid(payForm.getUserOpenid())
                .totalFee(totalFee)
                .clientIp(CommonUtil.getClientIp(request))
                .build();
    }

    /**
     * 微信支付统一下单接口，即充值接口
     * 调用预支付接口，获得前端调用支付接口需要的五个参数 和 sign：appId、timeStamp（下单时间）、nonceStr（随机字符串）
     * package：统一下单接口返回的 prepay_id 参数值，格式如：prepay_id=wx2017033010242291fcfe0db70013231072
     * signType：签名类型，默认为MD5
     * sign ： 以上数据的加密字符串
     */
    @PostMapping("/prepay")
    public ResponseEntity prePay(@RequestBody @Valid PayForm payForm, HttpServletRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //表单校验有误
            log.error("[微信统一下单]参数不正确，payForm={}", payForm);
            String msg = bindingResult.getFieldError() == null ? ResultEnum.PARAM_ERROR.getMsg()
                    : bindingResult.getFieldError().getDefaultMessage();
            throw new SubstituteException(msg, ResultEnum.PARAM_ERROR.getCode());
        }
        //支付统一下单
        wxPayService.prePay(createWxPrePayDto(payForm, request));
        return ResultUtil.success();
    }

    /**
     * 微信小程序支付，异步回调接口
     * 注意：同样的通知可能会多次发送给商户系统。商户系统必须能够正确处理重复的通知。
     *
     * @param notifyData 是一个xml格式的数据，包含支付成功后的一些信息
     * @return 返回xml，提醒微信已接收并处理该回调结果，否则会一直回调
     * 注意：方法名不能叫notify，否则会报错：无法覆盖java.lang.Object中的notify()
     */
    @GetMapping("/notify")
    public String wxNotify(String notifyData) {
        Map<String, String> result = new HashMap<>();
        result.put("return_code", "SUCCESS");
        result.put("return_msg", "OK");
        wxPayService.notify(notifyData);
        //修改完后返回xml告诉微信处理结果，不然微信会一直发送异步通知，则该方法会一直被调用
        System.out.println(XmlUtil.parseMap2Xml(result));
        return XmlUtil.parseMap2Xml(result);
    }


    //todo 用于测试短信发送
    @GetMapping("/test")
    public ResponseEntity testSms() {
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:,短信接收号码,支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码
        request.setPhoneNumbers("18185738567");
        //必填:短信签名名称-可在短信控制台中找到
        request.setSignName("testCx");
        //必填:短信模板ID-可在短信控制台中找到
        request.setTemplateCode("SMS_150865237");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${param1},您的验证码为${param2}"时,此处的值为
        Map<String, String> params = new HashMap<>();
        params.put("param1", "test1");
        params.put("param2", "test2");
        request.setTemplateParam(JSON.toJSON(params).toString());
        try {
            SmsUtil.sendSms(request, aliSmsConfig);
        } catch (ClientException e) {
            log.info("出错了");
        }
        return ResultUtil.success();
    }
}
