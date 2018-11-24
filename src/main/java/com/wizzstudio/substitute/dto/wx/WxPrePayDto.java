package com.wizzstudio.substitute.dto.wx;

import lombok.Builder;
import lombok.Data;

/**
 * 微信支付预下单Dto
 * Created By Cx On 2018/11/7 10:53
 */
@Data
@Builder
public class WxPrePayDto {

    //必填，商户系统订单号，由商户自定义生成，要求32个字符内，只能是数字、大小写字母_-|* 且 在同一个商户号下唯一
    //重新发起支付要使用原订单号，避免重复支付；已支付或已调用关单、撤销的订单号不能重新发起支付。
    private String indentId;
    //必填，订单总金额，金额单位为【分】，参数值不能带小数。
    private int totalFee;
    //必填，APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
    private String clientIp;
    //非必填，用户标识，trade_type=JSAPI，此参数必传（微信小程序支付必传），用户在商户appid下的唯一标识
    private String openid;

}
