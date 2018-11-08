package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.form.PayForm;

/**
 * 微信支付相关接口
 * Created By Cx On 2018/11/6 11:15
 */
public interface PayService {

    /**
     * 微信统一预下单
     * 详情见：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1&index=1
     */
    String prePay(PayForm payForm, String clientIP);
}
