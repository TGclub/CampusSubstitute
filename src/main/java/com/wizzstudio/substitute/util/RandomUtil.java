package com.wizzstudio.substitute.util;


import java.util.Random;

/**
 * 用于生成随机字符串
 * Created By Cx On 2018/10/25 22:16
 */
public class RandomUtil {

    private static final char[] words = {'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f', 'G', 'g', 'H', 'h', 'I', 'i', 'J', 'j', 'K', 'k', 'L', 'l', 'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T', 't', 'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x', 'Y', 'y', 'Z', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * 生成六位(数字+大小写字母)的随机字符串
     */
    public static String getSixRandom() {
        return getRandomString(6);
    }


    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            key.append(words[random.nextInt(words.length)]);
        }
        return key.toString();
    }

    /**
     * 生成唯一主键
     * 在一毫秒内产生冲突的可能性是 1/10000
     * synchronized关键字，防止多线程冲突
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        //生成一个四位的随机数
        String number = String.valueOf(random.nextInt(9000) + 1000);
        return System.currentTimeMillis() + number;
    }

}
