package com.hbhs.xb.sock.analysis.util;

import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by walter.xu on 2016/12/22.
 */
public class CommonUtil {

    public static Integer praseInt(String arg0, int defaultValue){
        if (!StringUtils.isEmpty(arg0))
            try {
                return Integer.valueOf(arg0);
            } catch (Exception e){}
        return defaultValue;
    }
    public static Double praseDouble(String arg0, double defaultValue){
        if (!StringUtils.isEmpty(arg0))
            try {
                return Double.valueOf(arg0);
            } catch (Exception e){}
        return defaultValue;
    }
    public static Long praseLong(String arg0, long defaultValue){
        if (!StringUtils.isEmpty(arg0))
            try {
                return Long.valueOf(arg0);
            } catch (Exception e){}
        return defaultValue;
    }

    public static String formatLongWithComma(long data){
        String result = data%1000 + "";
        data = data/1000;
        while(data!=0){
            result = data%1000+","+result;
            data = data/1000;
        }
        return result;
    }
    public static boolean isEmpty(String str){
        return str==null||"".equals(str.trim());
    }

    public static Date getBeiJinDate(Date date){
        Calendar cal  = Calendar.getInstance();
        if (date!=null) cal.setTime(date);
        // 获取当前时间区域差值，采用shanghai时间
        int timeForShangHaiOffset = 8*60*60*1000;
        int currentServerOffset = TimeZone.getDefault().getRawOffset();
        cal.add(Calendar.MILLISECOND, -currentServerOffset);
        cal.add(Calendar.MILLISECOND, timeForShangHaiOffset); // 转换成北京时间
        return cal.getTime();
    }
}
