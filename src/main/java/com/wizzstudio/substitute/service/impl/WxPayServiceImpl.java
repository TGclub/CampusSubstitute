package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.config.WeChatAccountConfig;
import com.wizzstudio.substitute.constants.Constant;
import com.wizzstudio.substitute.dto.IndentWxPrePayDto;
import com.wizzstudio.substitute.dto.wx.WxPayAsyncResponse;
import com.wizzstudio.substitute.dto.wx.WxPrePayInfo;
import com.wizzstudio.substitute.enums.ResultEnum;
import com.wizzstudio.substitute.exception.SubstituteException;
import com.wizzstudio.substitute.domain.Indent;
import com.wizzstudio.substitute.domain.User;
import com.wizzstudio.substitute.service.IndentService;
import com.wizzstudio.substitute.service.WxPayService;
import com.wizzstudio.substitute.service.UserService;
import com.wizzstudio.substitute.util.*;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 微信支付相关接口
 * Created By Cx On 2018/11/6 11:15
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class WxPayServiceImpl implements WxPayService {

    @Autowired
    UserService userService;
    @Autowired
    IndentService indentService;
    @Autowired
    WeChatAccountConfig weChatAccountConfig;

    /**
     * 签名算法用于计算签名值
     * 详见：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=4_3
     * 按照参数名ASCII码从小到大排序（字典序），
     * 使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。
     * 在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，
     * 再将得到的字符串所有字符转换为大写，得到sign值signValue。
     * @param fieldMap 支付信息对象
     * @return MD5签名过的字符串
     */
    private String getMD5Sign(Map<String,String> fieldMap){
        StringBuilder sbA = new StringBuilder();
        //该list用于存储所有字段名，并通过ASCII码排序,最后从fieldMap中依次取出,fieldMap的key为fieldName，value为该name对应的值
        List<String> fieldNames = new ArrayList<>(fieldMap.keySet());
        //按ASCII码顺序排序
        Collections.sort(fieldNames);
        //拼接成字符串stringA
        for (String fieldName : fieldNames) {
            sbA.append(fieldName.concat("=").concat(fieldMap.get(fieldName)).concat("&"));
        }
        //最后加上key
        sbA.append("key=").append(weChatAccountConfig.getMchKey());
        log.info("[微信统一下单]排序后的拼接参数：".concat(sbA.toString()));
        //MD5加密，并转换为大写
        return DigestUtils.md5DigestAsHex(sbA.toString().trim().getBytes()).toUpperCase();
    }

    /**
     * 组装统一下单需要的PayInfo对象
     */
    private WxPrePayInfo createPayInfo(IndentWxPrePayDto indentWxPrePayDto){
        //构建WxPayInfo对象
        WxPrePayInfo wxPrePayInfo = WxPrePayInfo.builder().appid(weChatAccountConfig.getAppid())
                .mch_id(weChatAccountConfig.getMchId())
                .nonce_str(RandomUtil.getRandomString(32))
                .notify_url(weChatAccountConfig.getNotifyUrl())
                .trade_type(Constant.WxPay.TRADE_TYPE)
                .openid(indentWxPrePayDto.getOpenid())
                .body(Constant.WxPay.PAY_BODY)
                .total_fee(indentWxPrePayDto.getTotalFee())
                .out_trade_no(indentWxPrePayDto.getIndentId())
                .spbill_create_ip(indentWxPrePayDto.getClientIp())
                //todo 是否可删
                .device_info("WEB")
                //todo 是否可删,默认即为MD5
                .sign_type("MD5")
                //todo 是否可删
                .attach("支付测试4luluteam")
                //todo 是否可删
                .limit_pay("no_credit")
                .build();

        //todo timeStart和timeExpire是否可以删掉——跑通了再测试
        Date date = new Date();
        String timeStart = TimeUtil.getFormatTime(date, Constant.WxPay.TIME_FORMAT);
        //过期时间为半小时
        String timeExpire = TimeUtil.getFormatTime(new Date(date.getTime()+30 * 60 * 1000), Constant.WxPay.TIME_FORMAT);
        wxPrePayInfo.setTime_start(timeStart);
        wxPrePayInfo.setTime_expire(timeExpire);

        //将PayInfo内非空参数签名
        //getFields()获得的是公有的字段，DeclaredFields是获取所有声明的，无论权限（不包括父类）
        Field[] fields = wxPrePayInfo.getClass().getDeclaredFields();
        //该list用于存储所有字段名，并通过ASCII码排序,最后从fieldMap中依次取出,fieldMap的key为fieldName，value为该name对应的值
        Map<String,String> fieldMap = new HashMap<>();
        try {
            for (Field field : fields){
                //设置对象的访问权限，保证对private的属性的访问
                field.setAccessible(true);
                //获取payInfo对象field字段的值,如果为空，则查看下一个
                Object o = field.get(wxPrePayInfo);
                if (o == null) continue;
                fieldMap.put(field.getName(), field.get(wxPrePayInfo).toString());
            }
        }catch (IllegalAccessException e){
            log.error("[微信统一下单]服务器异常");
            throw new SubstituteException(ResultEnum.INNER_ERROR);
        }
        //设置Sign
        wxPrePayInfo.setSign(getMD5Sign(fieldMap));

        return wxPrePayInfo;
    }

    /**
     * 用户统一预下单
     * 详见： https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1&index=1
     * 返回微信小程序前端调起支付API时需要的五个参数：
     */
    @Override
    public Map<String,String> prePay(IndentWxPrePayDto indentWxPrePayDto) {
        User user = userService.findUserByOpenId(indentWxPrePayDto.getOpenid());
        if (user == null){
            log.error("[微信统一下单]用户不存在，userId={}",indentWxPrePayDto.getOpenid());
            throw new SubstituteException(ResultEnum.USER_NOT_EXISTS);
        }
        log.info("[微信统一下单]用户:{} 进行预下单",indentWxPrePayDto.getOpenid());
        WxPrePayInfo wxPrePayInfo = createPayInfo(indentWxPrePayDto);


        //todo，封装参数为xml
        String xml = XmlUtil.payInfoToXML(wxPrePayInfo);
        log.info(xml);
        xml = xml.replace("__", "_").replace("<![CDATA[1]]>", "1");
        log.info(xml);

        //向微信接口发送参数，获取返回值并转换为map
        Map<String, String> result;
        try {
            StringBuffer buffer = HttpUtil.httpsRequest(Constant.WxPay.UNIFIED_ORDER_URL, "POST", xml);
            log.info("统一预下单返回参数如下: \n".concat(buffer.toString()));
            result = XmlUtil.parseXml2Map(buffer.toString());
        }catch (IOException e){
            log.error("[微信统一下单]调用微信统一下单接口失败,requestXml={}",xml);
            throw new SubstituteException(ResultEnum.INNER_ERROR);
        }catch (DocumentException e){
            log.error("[微信统一下单]微信统一下单接口获取信息转换Map失败");
            throw new SubstituteException(ResultEnum.INNER_ERROR);
        }

        //判断请求是否成功,SUCCESS/FAIL,此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
        String return_code = result.get("return_code");
        //返回信息，如非OK，则为错误原因
        String return_msg = result.get("return_msg");
        Map<String,String> returnParam = new HashMap<>();
        if("SUCCESS".equals(return_code) && "OK".equals(return_msg)) {
            returnParam.put("package","prepay_id=".concat(result.get("prepay_id")));
            returnParam.put("appId",wxPrePayInfo.getAppid());
            returnParam.put("nonceStr",RandomUtil.getRandomString(32));
            returnParam.put("signType","MD5");
            returnParam.put("timeStamp",String.valueOf(new Date().getTime()));
            returnParam.put("sign",getMD5Sign(returnParam));
        }
        else {
            //如果交易失败
            log.error("[微信统一下单]统一下单错误！,return_msg={}",return_msg);
            throw new SubstituteException(return_msg,ResultEnum.INNER_ERROR.getCode());
        }
        return returnParam;
    }


    /**
     * 处理异步通知注意事项：
     *  1.验证签名是否正确。防止他人模拟发送一个异步请求
     *  2.验证支付状态是否为支付成功
     *  3.验证支付金额是否正确
     *  4.验证支付人（支付人 == 下单人）   按需验证，不需要可以不验证，其他都必须验证
     *
     */
    @Override
    public void notify(String notifyData) {
        Map<String,String> notifyMap;
        WxPayAsyncResponse asyncResponse;
        try {
            //xml解析为map
            notifyMap = XmlUtil.parseXml2Map(notifyData);
            //xml解析为对象
            asyncResponse = (WxPayAsyncResponse) XmlUtil.parseXml2Object(notifyData, WxPayAsyncResponse.class);
            if (asyncResponse == null) throw new Exception();
        } catch (Exception e) {
            log.error("【微信支付异步通知】notifyData格式有误, notifyData={}", notifyData);
            throw new SubstituteException("【微信支付异步通知】notifyData格式有误");
        }

        //1.验证签名是否正确。防止他人模拟发送一个异步请求
        if (!getMD5Sign(notifyMap).equals(notifyMap.get("sign"))) {
            log.error("【微信支付异步通知】签名验证失败, response={}", notifyData);
            throw new SubstituteException("【微信支付异步通知】签名验证失败");
        }

        //2.验证支付状态是否为支付成功
        if(!"SUCCESS".equals(asyncResponse.getReturnCode()) || !"SUCCESS".equals(asyncResponse.getResultCode())) {
            log.error("【微信支付异步通知】发起支付失败, returnCode 或 asyncResponse.getResultCode() != SUCCESS, returnMsg = {}"
                    , asyncResponse.getReturnMsg());
            throw new SubstituteException("【微信支付异步通知】发起支付失败");
        }

        //3.验证支付金额是否正确
        log.info("[微信支付]，异步通知，asyncResponse={}",asyncResponse);

        //查询订单，并判断订单是否存在，金额是否一致
        Indent indent = indentService.getSpecificIndentInfo(Integer.valueOf(asyncResponse.getOutTradeNo()));
        if(indent == null){
            log.error("[微信支付]，异步通知，订单不存在,orderId={}",asyncResponse.getOutTradeNo());
            throw new SubstituteException(ResultEnum.INDENT_NOT_EXISTS);
        }
        //todo 这样比较不知道对不对
        if (asyncResponse.getTotalFee() == indent.getIndentPrice().movePointRight(2).intValue()){
            log.error("[微信支付]，异步通知，订单金额不一致,orderId={},订单金额={},异步通知金额={}",asyncResponse.getOutTradeNo(),
                    indent.getIndentPrice(),asyncResponse.getTotalFee()/100);
            throw new SubstituteException(ResultEnum.WX_NOTIFY_MONEY_VERIFY_ERROR);
        }

        //todo 修改订单支付状态
        //indentService.paid()

    }

}
