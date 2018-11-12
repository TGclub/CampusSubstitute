package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.dto.IndentWxPrePayDto;

import java.util.Map;

/**
 * 微信支付相关接口
 * Created By Cx On 2018/11/6 11:15
 */
public interface WxPayService {

    /**
     * 微信统一预下单
     * 详情见：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1&index=1
     */
    Map<String,String> prePay(IndentWxPrePayDto indentWxPrePayDto);

    /**
     * 支付异步通知，当支付成功后该方法会接收到通知
     * @param notifyData 是一个xml格式的数据，包含支付成功后的一些信息
     * @return
     */
    void notify(String notifyData);
}
