package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.dto.IndentWxPrePayDto;
import com.wizzstudio.substitute.form.PayForm;

import java.util.Map;

/**
 * 微信支付相关接口
 * Created By Cx On 2018/11/6 11:15
 */
public interface PayService {

    /**
     * 微信统一预下单
     * 详情见：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1&index=1
     */
    Map<String,String> prePay(IndentWxPrePayDto indentWxPrePayDto);
}
