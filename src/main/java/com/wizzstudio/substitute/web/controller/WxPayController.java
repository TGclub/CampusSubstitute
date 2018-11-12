package com.wizzstudio.substitute.web.controller;

import com.wizzstudio.substitute.enums.ResultEnum;
import com.wizzstudio.substitute.exception.SubstituteException;
import com.wizzstudio.substitute.form.PayForm;
import com.wizzstudio.substitute.service.WxPayService;
import com.wizzstudio.substitute.util.CommonUtil;
import com.wizzstudio.substitute.util.ResultUtil;
import com.wizzstudio.substitute.util.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created By Cx On 2018/11/6 22:09
 */
@Controller
@Slf4j
@RequestMapping("wxpay/")
public class WxPayController {

    @Autowired
    WxPayService wxPayService;

    /**
     * 微信支付统一下单接口
     */
    @PostMapping("/prepay")
    @ResponseBody
    public ResponseEntity prePay(@RequestBody @Valid PayForm payForm, HttpServletRequest request, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            //表单校验有误
            log.error("[微信统一下单]参数不正确，payForm={}", payForm);
            String msg = bindingResult.getFieldError() == null ? ResultEnum.PARAM_ERROR.getMsg()
                    : bindingResult.getFieldError().getDefaultMessage();
            throw new SubstituteException(msg, ResultEnum.PARAM_ERROR.getCode());
        }
        //获取客户端IP
        String clientIP = CommonUtil.getClientIp(request);
        //支付统一下单
        //wxPayService.prePay(payForm,clientIP);
        return ResultUtil.success();
    }

    /**
     * 微信小程序支付，异步回调接口
     * 注意：同样的通知可能会多次发送给商户系统。商户系统必须能够正确处理重复的通知。
     * @param notifyData 是一个xml格式的数据，包含支付成功后的一些信息
     * @return 返回xml，提醒微信已接收并处理该回调结果，否则会一直回调
     */
    @PostMapping("/notify")
    public String notify(@RequestBody String notifyData){
        Map<String,String> result = new HashMap<>();
        result.put("return_code","SUCCESS");
        result.put("return_msg","OK");
        wxPayService.notify(notifyData);
        //修改完后返回xml告诉微信处理结果，不然微信会一直发送异步通知，则该方法会一直被调用
        return XmlUtil.parseMap2Xml(result);
    }
}
