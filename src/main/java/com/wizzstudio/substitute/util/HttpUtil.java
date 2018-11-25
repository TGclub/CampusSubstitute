package com.wizzstudio.substitute.util;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created By Cx On 2018/11/7 19:23
 */
public class HttpUtil {

    /**
     * 以HTTPS的方式，与requestUrl以requestMethod的方式进行通信
     *
     * @param requestUrl    通信url
     * @param requestMethod 通信方法GET、POST、PUT
     * @param output        请求参数
     * @return URL响应返回的StringBuffer
     */
    public static StringBuffer httpsRequest(String requestUrl, String requestMethod, String output) throws IOException {
        URL url = new URL(requestUrl);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        //URL 连接可用于输入和/或输出。将标志设置为 true，指示应用程序要将数据写入 URL 连接 或 从URL中读取数据。
        connection.setDoOutput(true);
        connection.setDoInput(true);
        //如果为 true，则只要有条件就允许协议使用缓存。如果为 false，则该协议始终必须获得此对象的新副本
        connection.setUseCaches(false);
        //设置访问的方式，POST、GET……
        connection.setRequestMethod(requestMethod);
        if (null != output) {
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(output.getBytes(StandardCharsets.UTF_8));
            outputStream.close();
        }
        // 从输入流读取返回内容
        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str;
        StringBuffer buffer = new StringBuffer();
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        connection.disconnect();
        return buffer;
    }
}
