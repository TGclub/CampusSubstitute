package com.wizzstudio.substitute.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.wizzstudio.substitute.dto.WxPrePayInfo;
import com.wizzstudio.substitute.enums.BaseEnum;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created By Cx On 2018/11/6 11:30
 */
public class CommonUtil {

    /**
     * 获取客户端IP
     *
     * 如果用反向代理软件，将http://ip:port/ 的URL反向代理为http://www.xxx.com/ 的URL时，
     * 当我们访问http://www.xxx.com/index.jsp/ 时，并不是我们访问服务器,
     * 而是先由代理服务器去访问http://ip:port/index.jsp ，代理服务器再将访问到的结果返回给我们的浏览器，
     * 所以通过request.getRemoteAddr()的方法获取的IP实际上是代理服务器的地址
     * 即：127.0.0.1　或　192.168.1.110，不是客户端的真实ＩＰ。
     *
     * 但在转发请求的HTTP头信息中，增加了X－FORWARDED－FOR信息。用以跟踪原有的客户端IP地址和原来客户端请求的服务器地址。
     * 有时访问http://www.xxx.com/index.jsp/ 时，返回的IP地址始终是unknown，不是如上所说的127.0.0.1 或 92.168.1.110，
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 对字符串md5加密
     */
    public static String getMD5(String str){
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    /**
     * 遍历enumClass获取和EnumString匹配的Enum并返回
     */
    public static <T extends BaseEnum>T getEnum(String enumString,Class<T> enumClass){
        for (T baseEnum : enumClass.getEnumConstants()){
            if (baseEnum.toString().equals(enumString)) return baseEnum;
        }
        return null;
    }

    /**
     * 将微信预支付信息转换为XML
     * XStream会很玄学的将字段中的_更改为__,所以需要replace
     */
    public static String payInfoToXML(WxPrePayInfo wxPrePayInfo) {
        //todo 按官方文档，这里只需要进行简单的XML转换，不需要在每个字段值上都套上<![CDATA[xxxx]]>，所以按理不用特定XStream
        //XStream xStream = new XStream();
        XStream xstream = new XStream(new XppDriver() {
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    //增加CDATA标记
                    boolean cdata = true;
                    public void startNode(String name, Class clazz) {
                        super.startNode(name, clazz);
                    }
                    protected void writeText(QuickWriter writer, String text) {
                        if (cdata) {
                            writer.write("<![CDATA[");
                            writer.write(text);
                            writer.write("]]>");
                        } else {
                            writer.write(text);
                        }
                    }
                };
            }
        });
        xstream.alias("xml", wxPrePayInfo.getClass());
        return xstream.toXML(wxPrePayInfo);
    }

    /**
     * 将XML转换为Map并返回
     */
    public static Map<String, String> parseXml2Map(String xml) throws DocumentException {
        Map<String, String> map = new HashMap<>();
        //读取xml字符串
        Document document = DocumentHelper.parseText(xml);
        //获取根节点
        Element root = document.getRootElement();
        //获取根节点下的子节点列表
        List<Element> elementList = root.elements();
        //遍历子节点，并以key-value的形式存入map
        for (Element e : elementList){
            map.put(e.getName(), e.getText());
        }
        return map;
    }

}
