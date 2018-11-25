package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.dto.wx.WxPrePayDto;

import java.util.Map;

/**
 * 微信支付相关接口
 * Created By Cx On 2018/11/6 11:15
 */
public interface WxPayService {

    /**
     * 微信统一预下单
     * 详情见：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1&index=1
     * 返回前端调用支付接口需要的五个参数 和 sign：
     * appId
     * timeStamp（下单时间戳）
     * nonceStr（随机字符串，32位以内）
     * package：统一下单接口返回的 prepay_id 参数值，格式如：prepay_id=wx2017033010242291fcfe0db70013231072
     * signType：签名类型，默认为MD5
     * sign ： 以上数据的加密字符串
     */
    Map<String, String> prePay(WxPrePayDto wxPrePayDto);

    /**
     * 支付异步通知，当支付成功后该方法会接收到通知
     *
     * @param notifyData 是一个xml格式的数据，包含支付成功后的一些信息
     */
    void notify(String notifyData);
}
