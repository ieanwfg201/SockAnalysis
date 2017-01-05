package com.hbhs.xb.sock.analysis.dao.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by walter.xu on 2016/12/28.
 */
public class DateMetrix {
    private long day;
    private long hour;
    private long minute1;
    private long minute5;
    private long minute10;
    private long minute15;
    private long minute30;
    private long second10;
    private long second30;

    public void date(Date date) {
        long temp = Long.valueOf(new SimpleDateFormat("yyyyMMddHHmmss").format(date));
        day = temp/1000000; temp = temp%1000000;
        long second = temp%100; temp = temp/100;
        second10 = temp*100+(second/10)*10;
        second30 = temp*100+(second/30)*30;
        long minute = temp%100; temp = temp/100;
        minute1 = temp*100+minute;
        minute5 = temp*100+(minute/5)*5;
        minute10 = temp*100+(minute/10)*10;
        minute15 = temp*100+(minute/15)*15;
        minute30 = temp*100+(minute/30)*30;
        hour = temp;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public long getHour() {
        return hour;
    }

    public void setHour(long hour) {
        this.hour = hour;
    }

    public long getSecond10() {
        return second10;
    }

    public void setSecond10(long second10) {
        this.second10 = second10;
    }

    public long getSecond30() {
        return second30;
    }

    public void setSecond30(long second30) {
        this.second30 = second30;
    }

    public long getMinute1() {
        return minute1;
    }

    public void setMinute1(long minute1) {
        this.minute1 = minute1;
    }

    public long getMinute5() {
        return minute5;
    }

    public void setMinute5(long minute5) {
        this.minute5 = minute5;
    }

    public long getMinute10() {
        return minute10;
    }

    public void setMinute10(long minute10) {
        this.minute10 = minute10;
    }

    public long getMinute30() {
        return minute30;
    }

    public void setMinute30(long minute30) {
        this.minute30 = minute30;
    }

    public long getMinute15() {
        return minute15;
    }

    public void setMinute15(long minute15) {
        this.minute15 = minute15;
    }
}
