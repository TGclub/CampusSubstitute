package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.config.WeChatAccountConfig;
import com.wizzstudio.substitute.constants.Constant;
import com.wizzstudio.substitute.dto.IndentWxPrePayDto;
import com.wizzstudio.substitute.dto.WxPrePayInfo;
import com.wizzstudio.substitute.enums.ResultEnum;
import com.wizzstudio.substitute.exception.SubstituteException;
import com.wizzstudio.substitute.pojo.User;
import com.wizzstudio.substitute.service.IndentService;
import com.wizzstudio.substitute.service.PayService;
import com.wizzstudio.substitute.service.UserService;
import com.wizzstudio.substitute.util.CommonUtil;
import com.wizzstudio.substitute.util.HttpUtil;
import com.wizzstudio.substitute.util.RandomUtil;
import com.wizzstudio.substitute.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class PayServiceImpl implements PayService {

    @Autowired
    UserService userService;
    @Autowired
    IndentService indentService;
    @Autowired
    WeChatAccountConfig weChatAccountConfig;


    /**
     * 签名算法用于计算签名值
     * 详见：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=4_3
     * 将PayInfo内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），
     * 使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。
     * 在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，
     * 再将得到的字符串所有字符转换为大写，得到sign值signValue。
     */
    private String getSign(WxPrePayInfo wxPrePayInfo){
        StringBuilder sbA = new StringBuilder();
        //getFields()获得的是公有的字段，DeclaredFields是获取所有声明的，无论权限（不包括父类）
        Field[] fields = wxPrePayInfo.getClass().getDeclaredFields();
        //该list用于存储所有字段名，并通过ASCII码排序,最后从fieldMap中依次取出,fieldMap的key为fieldName，value为该name对应的值
        List<String> fieldNames = new ArrayList<>();
        Map<String,String> fieldMap = new HashMap<>();
        try {
            for (Field field : fields){
                //设置对象的访问权限，保证对private的属性的访问
                field.setAccessible(true);
                //获取payInfo对象field字段的值,如果为空，则查看下一个
                Object o = field.get(wxPrePayInfo);
                if (o == null) continue;
                fieldNames.add(field.getName());
                fieldMap.put(field.getName(), field.get(wxPrePayInfo).toString());
            }
        }catch (IllegalAccessException e){
            log.error("[微信统一下单]服务器异常");
            throw new SubstituteException(ResultEnum.INNER_ERROR);
        }
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
                //todo 是否需要把订单交易号改为String，防止被遍历
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

        //设置Sign
        wxPrePayInfo.setSign(getSign(wxPrePayInfo));

        return wxPrePayInfo;
    }

    /**
     * 用户统一预下单
     * 详见： https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1&index=1
     */
    @Override
    public String prePay(IndentWxPrePayDto indentWxPrePayDto) {
        User user = userService.findUserByOpenId(indentWxPrePayDto.getOpenid());
        if (user == null){
            log.error("[微信统一下单]用户不存在，userId={}",indentWxPrePayDto.getOpenid());
            throw new SubstituteException(ResultEnum.USER_NOT_EXISTS);
        }
        log.info("[微信统一下单]用户:{} 进行预下单",indentWxPrePayDto.getOpenid());
        WxPrePayInfo wxPrePayInfo = createPayInfo(indentWxPrePayDto);


        //todo
        String xml = CommonUtil.payInfoToXML(wxPrePayInfo);
        log.info(xml);
        xml = xml.replace("__", "_").replace("<![CDATA[1]]>", "1");
        log.info(xml);

        Map<String, String> result;
        try {
            StringBuffer buffer = HttpUtil.httpsRequest(Constant.WxPay.UNIFIED_ORDER_URL, "POST", xml);
            log.info("统一预下单返回参数如下: \n".concat(buffer.toString()));
            result = CommonUtil.parseXml2Map(buffer.toString());
        }catch (IOException e){
            log.error("[微信统一下单]调用微信统一下单接口失败");
            throw new SubstituteException(ResultEnum.INNER_ERROR);
        }catch (DocumentException e){
            log.error("[微信统一下单]微信统一下单接口获取信息转换Map失败");
            throw new SubstituteException(ResultEnum.INNER_ERROR);
        }

        String return_code = result.get("return_code");
        if("SUCCESS".equals(return_code)) {
            //如果返回成功
            String return_msg = result.get("return_msg");
            if(!"OK".equals(return_msg)) {

                log.info("统一下单错误！");
                return "";
            }
            return result.get("prepay_id");
        }
        else {
            return "";
        }
    }
}
