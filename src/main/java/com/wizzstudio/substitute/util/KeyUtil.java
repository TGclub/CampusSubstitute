package com.wizzstudio.substitute.util;

import com.wizzstudio.substitute.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 用于生成唯一主键
 * Created By Cx On 2018/10/25 22:16
 */
public class KeyUtil {

    /**
     * 生成六位(数字+大小写字母)的随机字符串
     */
    public static String getRandomKey() {
        final char[] words = {'A','a','B','b','C','c','D','d','E','e','F','f','G','g','H','h','I','i','J','j','K','k','L','l','M','m','N','n','O','o','P','p','Q','q','R','r','S','s','T','t','U','u','V','v','W','w','X','x','Y','y','Z','z','0','1','2','3','4','5','6','7','8','9'};
        Random random = new Random();
        //生成一个六位数字+字母的随机字符串
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 6; i++){
            key.append(words[random.nextInt(words.length)]);
        }
        return key.toString();
    }

}
