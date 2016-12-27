package com.hbhs.xb.sock.config.util;

import com.hbhs.xb.sock.analysis.RateUnitEnum;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by walter.xu on 2016/12/23.
 */
public class ScheduleConfigBean {

    public Integer SOCK_LOADER_START_TIME = 900; // means 09:00
    public Integer SOCK_LOADER_END_TIME = 1530;  // means 15: 30

    public String SOCK_LOADER_CRON = "0/10 * * * * *";

    public boolean isCurrentHourTimeIsBetweenConfiguredStartAndEndTime(){
        Calendar cal  = Calendar.getInstance();
        // 获取当前时间区域差值，采用shanghai时间
        int timeForShangHaiOffset = 8*60*60*1000;
        int currentServerOffset = TimeZone.getDefault().getRawOffset();
        cal.add(Calendar.MILLISECOND, -currentServerOffset);
        cal.add(Calendar.MILLISECOND, timeForShangHaiOffset); // 转换成北京时间
        Integer currentHourTime = Integer.valueOf(new SimpleDateFormat("HHmm").format(cal.getTime()));
        return currentHourTime>SOCK_LOADER_START_TIME&&currentHourTime<SOCK_LOADER_END_TIME;
    }

}
