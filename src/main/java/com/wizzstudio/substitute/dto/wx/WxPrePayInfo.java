package com.wizzstudio.substitute.dto.wx;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信统一下单所需参数信息
 * 详情见：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1&index=1
 * Created By Cx On 2018/11/6 19:16
 */
@Data
@Builder
@AllArgsConstructor
public class WxPrePayInfo implements Serializable {

    private static final long serialVersionUID = -7187359421292513037L;

    //必填，微信分配的小程序ID
    private String appid;
    //必填，微信支付分配的商户号
    private String mch_id;
    //必填，随机字符串，长度要求在32位以内，可使用KeyUtil中的getRandomString(32)生成
    private String nonce_str;
    //必填，通过签名算法计算得出的签名值，具体实现见 WxPayServiceImpl -> getSign() 方法
    private String sign;
    //必填，商品简单描述（最多128个字），如：腾讯充值中心-QQ会员充值
    private String body;
    //必填，商户系统订单号，由商户自定义生成，要求32个字符内，只能是数字、大小写字母_-|* 且 在同一个商户号下唯一
    //重新发起支付要使用原订单号，避免重复支付；已支付或已调用关单、撤销的订单号不能重新发起支付。
    private String out_trade_no;
    //必填，订单总金额，金额单位为【分】，参数值不能带小数。
    private int total_fee;
    //必填，APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
    private String spbill_create_ip;
    //必填，异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
    private String notify_url;
    //必填，交易类型，JSAPI--小程序和公众号支付、NATIVE--原生扫码支付、APP--app支付、
    //MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口，
    // trade_type=NATIVE时（即扫码支付），product_id必传
    // trade_type=JSAPI，openid必传
    private String trade_type;
    //非必填，用户标识，trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识
    private String openid;


    //非必填，自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页、公众号或小程序内支付可以传"WEB"
    private String device_info;
    //非必填，签名类型，默认为MD5，支持HMAC-SHA256和MD5。该项目 getSign() 方法使用的是MD5加密
    private String sign_type;
    //非必填，商品详细描述（JSON格式，最多600字），对于使用单品优惠的商户，该字段必须按照规范上传
    //详见 https://pay.weixin.qq.com/wiki/doc/api/danpin.php?chapter=9_102&index=2
    private String detail;
    //非必填，附加数据（最多127个字），在查询API和支付通知中原样返回，可作为自定义参数使用
    private String attach;
    //非必填，符合ISO 4217标准的三位字母代码，默认人民币：CNY。好像暂时只支持CNY，所以不用管该字段
    private String fee_type;
    //非必填，订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
    private String time_start;
    //非必填，订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。
    //订单失效时间是针对订单号而言的，由于在请求支付的时候有一个必传参数prepay_id只有两小时的有效期，
    //所以在重入时间超过2小时的时候需要重新请求下单接口获取新的prepay_id
    //建议：最短失效时间间隔大于1分钟
    private String time_expire;
    //非必填，订单优惠标记，使用代金券或立减优惠功能时需要的参数
    //详见 https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_1
    private String goods_tag;
    //非必填，二维码中包含的商品ID,trade_type=NATIVE时（即扫码支付），此参数必传。商户自行定义。
    private String product_id;
    //非必填，指定支付方式，当等于no_credit时可限制用户不能使用信用卡支付
    private String limit_pay;

    public WxPrePayInfo(){}

}
