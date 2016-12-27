package com.hbhs.xb.sock.analysis.entity;

import com.hbhs.xb.sock.analysis.util.CommonUtil;

/**
 * Created by walter.xu on 2016/12/22.
 */
public class SockPlateSummary {
    public String plateID;
    public String plateName;
    public Integer companyCount;
    public double averagePrice;
    public double priceChange;
    public double priceChangePersent;
    public long dealNumber;
    public long dealAmount;
    public String aheadSockID;
    public String aheadSockName;
    public double aheadSockPrice;
    public double aheadSockPriceChange;
    public double aheadSockChangePersent;
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append(plateID).append("(").append(plateName).append("|").append(companyCount).append(") ")
                .append("Price: ").append(averagePrice).append("(").append(priceChange).append("|").append(priceChangePersent).append(")")
                .append(", dealNumber: ").append(CommonUtil.formatLongWithComma(dealNumber)).append(", dealAmount: ").append(CommonUtil.formatLongWithComma(dealAmount)).append("\n");
        str.append("------").append(aheadSockID).append("(").append(aheadSockName).append("|").append(aheadSockPrice)
                .append("|").append(aheadSockPriceChange).append("|").append(aheadSockChangePersent).append(")").append("\n");
        return str.toString();
    }
}
