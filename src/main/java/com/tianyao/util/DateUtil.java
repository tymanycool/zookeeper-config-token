package com.tianyao.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    /**
     * 得到当前时间
     * @return
     */
    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    /**
     * 将日期字符串转换成long毫秒数
     * @param str
     * @return
     */
    public static long getDateMills(String str){
        try {
            long dateMills = new SimpleDateFormat("yyyyMMddHHmmss").parse(str).getTime();
            return dateMills;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
