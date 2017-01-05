package com.hbhs.xb.sock.config.util;

import com.hbhs.xb.sock.analysis.RateUnitEnum;
import com.hbhs.xb.sock.analysis.util.CommonUtil;

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
        Integer currentHourTime = Integer.valueOf(new SimpleDateFormat("HHmm").format(CommonUtil.getBeiJinDate(null)));
        return currentHourTime>SOCK_LOADER_START_TIME&&currentHourTime<SOCK_LOADER_END_TIME;
    }

}
