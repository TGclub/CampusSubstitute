package com.wizzstudio.substitute.dto.wx;

import lombok.Data;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 微信支付异步回调返回参数接收类
 * 详见： https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_7
 * Created By Cx On 2018/11/12 10:23
 */
@Data
@Root(name = "xml", strict = false)
public class WxPayAsyncResponse {

    //返回状态码，此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
    @Element(name = "return_code")
    private String returnCode;

    //返回信息，如非空，为错误原因
    @Element(name = "return_msg", required = false)
    private String returnMsg;

    /** 以下字段在return_code为SUCCESS的时候有返回. */
    //微信分配的小程序ID
    @Element(name = "appid", required = false)
    private String appid;

    //微信支付分配的商户号
    @Element(name = "mch_id", required = false)
    private String mchId;

    //微信支付分配的终端设备号
    @Element(name = "device_info", required = false)
    private String deviceInfo;

    //随机字符串
    @Element(name = "nonce_str", required = false)
    private String nonceStr;

    //签名，https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=4_3
    @Element(name = "sign", required = false)
    private String sign;

    //业务结果
    @Element(name = "result_code", required = false)
    private String resultCode;

    //错误代码
    @Element(name = "err_code", required = false)
    private String errCode;

    //错误代码描述
    @Element(name = "err_code_des", required = false)
    private String errCodeDes;

    //用户标识
    @Element(name = "openid", required = false)
    private String openid;

    //用户是否关注公众账号，Y-关注，N-未关注
    @Element(name = "is_subscribe", required = false)
    private String isSubscribe;

    //交易类型，JSAPI--小程序和公众号支付、NATIVE--原生扫码支付、APP--app支付、
    @Element(name = "trade_type", required = false)
    private String tradeType;

    //付款银行类型，采用字符串类型的银行标识。详见：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=4_2
    @Element(name = "bank_type", required = false)
    private String bankType;

    //订单总金额，单位为分
    @Element(name = "total_fee", required = false)
    private Integer totalFee;

    //货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY
    @Element(name = "fee_type", required = false)
    private String feeType;

    //todo 订单现金支付金额，单位分(微信接口文档中给的是int类型)
    @Element(name = "cash_fee", required = false)
    private String cashFee;

    //货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY
    @Element(name = "cash_fee_type", required = false)
    private String cashFeeType;

    //todo 代金券金额<=订单金额，订单金额-代金券金额=现金支付金额，单位分(微信接口文档中给的是int类型)
    @Element(name = "coupon_fee", required = false)
    private String couponFee;

    //todo 代金券使用数量，单位分(微信接口文档中给的是int类型)
    @Element(name = "coupon_count", required = false)
    private String couponCount;

    //微信支付订单号
    @Element(name = "transaction_id", required = false)
    private String transactionId;

    //商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
    @Element(name = "out_trade_no", required = false)
    private String outTradeNo;

    //商家数据包，原样返回
    @Element(name = "attach", required = false)
    private String attach;

    //支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
    @Element(name = "time_end", required = false)
    private String timeEnd;

    //todo 微信接口文档中没有该字段
    @Element(name = "mweb_url", required = false)
    private String mwebUrl;

    /** 支付优惠时多返回字段 */
    //应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
    @Element(name = "settlement_total_fee", required = false)
    private Integer settlementTotalFee;

    @Element(name = "coupon_type", required = false)
    private String couponType;
}
