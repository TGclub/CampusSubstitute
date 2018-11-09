package com.wizzstudio.substitute.constants;

public interface Constant {
    int SYSTEM_BUSY_CODE = -1;
    int REQUEST_SUCCEED_CODE = 0;
    int INVALID_TOKEN = 40001;
    int INVALID_CERTIFICATE_TYPE = 40002;
    int INVALID_USER_ID = 40003;
    int INVALID_MSG_TYPE = 40008;

    String QUERY_SUCCESSFULLY = "请求成功";
    String QUERY_FAILED = "请求失败";
    String TOKEN = "token";
    String INVALID_MESSAGE = "信息有误";

    Integer TOKEN_EXPIRED = 7200;

    // 864000 = 60*60*24*10 （10天）
    Integer REMEMEMBER_ME = 864000;

    String TEST_HOST = "http://127.0.0.1";

    int TEST_PORT = 8001;

    interface WxPay{
        //微信支付统一下单时间格式
        String TIME_FORMAT = "yyyyMMddHHmmss";
        //微信小程序交易类型
        String TRADE_TYPE = "JSAPI";
        //微信支付交易大致描述
        String PAY_BODY = "校园速递支付";
        //微信预支付下单接口URL
        String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    }
}
