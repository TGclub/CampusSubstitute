package com.wizzstudio.substitute.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.wizzstudio.substitute.dto.wx.WxPrePayInfo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created By Cx On 2018/11/12 10:30
 */
public class XmlUtil {

    private static XStream xstream = new XStream(new XppDriver() {
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

    /**
     * 将微信预支付信息转换为XML
     * XStream会很玄学的将字段中的_更改为__,所以需要replace
     */
    public static String payInfoToXML(WxPrePayInfo wxPrePayInfo) {
        //todo 按官方文档，这里只需要进行简单的XML转换，不需要在每个字段值上都套上<![CDATA[xxxx]]>，所以按理不用特定XStream
        //XStream xStream = new XStream();
        xstream.alias("xml", wxPrePayInfo.getClass());
        return xstream.toXML(wxPrePayInfo).replace("__", "_");
    }

    /**
     * 将map转换为XML
     * XStream会很玄学的将字段中的_更改为__,所以需要replace
     */
    public static String parseMap2Xml(Map<String,String> map){
        xstream.alias("xml", map.getClass());
        return xstream.toXML(map).replace("__", "_");
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

    /**
     *  xml转对象
     */
    public static Object parseXml2Object(String xml, Class objClass) {
        Serializer serializer = new Persister();
        try {
            return serializer.read(objClass, xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
