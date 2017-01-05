package com.hbhs.xb.sock.analysis.dao.entity;

import com.hbhs.xb.sock.analysis.entity.DateInfo;

/**
 * Created by walter.xu on 2016/12/31.
 */
public class SockDetailMetrixEntiy extends SockDetailEntity {
    private DateInfo date;

    public DateInfo getDate() {
        return date;
    }

    public void setDate(DateInfo date) {
        this.date = date;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        if(date==null) str.append("[null]");
        else {
            str.append("[");
            str.append(date.getDay());
            if (date.getHour()!=null){
                str.append(" ").append(date.getHour());
                if (date.getMinute30()!=null)
                    str.append(":").append(date.getMinute30());
                else if (date.getMinute15()!=null)
                    str.append(":").append(date.getMinute15());
                else if (date.getMinute10()!=null)
                    str.append(":").append(date.getMinute10());
                else if (date.getMinute5()!=null)
                    str.append(":").append(date.getMinute5());
                else if (date.getMinute1()!=null)
                    str.append(":").append(date.getMinute1());

                if (date.getSecond30()!=null)
                    str.append(":").append(date.getSecond30());
                else if (date.getSecond10()!=null)
                    str.append(":").append(date.getSecond10());
            }
            str.append("]").append(" ");
        }

        str.append(super.getSockID()).append("-").append(super.getSockName()).append("[").append(super.getPlateID()).append("]");
        str.append(" ").append(super.getPrice()).append("[").append(super.getPriceMaxAtToday())
                .append("|").append(super.getPriceMinAtToday()).append("]");

        return str.toString();
    }
}
