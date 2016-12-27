package com.hbhs.xb.sock.analysis.util;

import org.springframework.util.StringUtils;

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
}
