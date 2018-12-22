package com.wizzstudio.substitute.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * Created By Cx On 2018/11/6 23:35
 */
public class TimeUtil {

    /**
     * 将Date转换成固定format格式的字符串并返回
     */
    public static String getFormatTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 将符合format(如：yyyy-MM-dd HH:mm:ss)格式的字符串day转成时间戳
     * 如changeDate(1999-01-01,yyyy-MM-dd )
     */
    public static Long changDate(String day, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(day);
            return date.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将时间戳转换为format格式的字符串
     */
    public static String stampToDate(Long s, String format) {
        if (s == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date(s);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当日23：59：59的时间
     */
    public static Date getLastTime(){
        String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取当日00：00：00的时间
     */
    public static Date getFirstTime(){
        String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(getLastTime().getTime() - new Date().getTime());
    }

}
