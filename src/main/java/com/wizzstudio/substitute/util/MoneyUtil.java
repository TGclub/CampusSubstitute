package com.wizzstudio.substitute.util;

import java.math.BigDecimal;

/**
 * Created By Cx On 2018/11/12 11:02
 */
public class MoneyUtil {

    /**
     * 元转分
     */
    public static Integer Yuan2Fen(BigDecimal yuan) {
        return yuan.movePointRight(2).intValue();
    }

    /**
     * 分转元
     */
    public static BigDecimal Fen2Yuan(Integer fen) {
        return new BigDecimal(fen).movePointLeft(2);
    }
}
