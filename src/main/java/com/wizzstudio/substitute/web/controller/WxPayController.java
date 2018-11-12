package com.wizzstudio.substitute.web.controller;

import com.wizzstudio.substitute.enums.ResultEnum;
import com.wizzstudio.substitute.exception.SubstituteException;
import com.wizzstudio.substitute.form.PayForm;
import com.wizzstudio.substitute.service.PayService;
import com.wizzstudio.substitute.util.CommonUtil;
import com.wizzstudio.substitute.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created By Cx On 2018/11/6 22:09
 */
@RestController
@Slf4j
@RequestMapping
public class PayController {

    @Autowired
    PayService payService;

    /**
     * 微信支付统一下单接口
     */
    @PostMapping
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
        //payService.prePay(payForm,clientIP);
        return ResultUtil.success();
    }

    @GetMapping
    public ResponseEntity test(){
        return ResultUtil.success(payService);
    }
}
