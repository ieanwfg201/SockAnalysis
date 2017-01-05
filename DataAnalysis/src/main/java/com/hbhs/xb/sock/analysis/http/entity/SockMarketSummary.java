package com.hbhs.xb.sock.analysis.http.entity;

import com.hbhs.xb.sock.analysis.util.CommonUtil;

/**
 * Created by walter.xu on 2016/12/22.
 * 股市summary信息
 */
public class SockMarketSummary {
    public String sockMarketID;        // 股市id
    public String sockMarketName;      // 股市名称
    public double priceOpenAtToday;         // 今天开
    public double priceCloseAtYesterday;    // 昨天收
    public double priceMaxAtToday;          // 今天最高
    public double priceMinAtToday;          // 今天最低
    public double price;      // 当前点数
    public String day;                 // 时间：yyyy-MM-dd
    public String time;                // 时间：HH:mm:ss
    public long dealNumber;          // 成交量
    public long dealAmount;          // 成交额

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("sockMarketID:").append(sockMarketID).append(", sockMarketName:").append(sockMarketName).append(", time: ").append(day).append(" ").append(time).append("\n");
        str.append("  ").append("今开:").append(priceOpenAtToday).append(", 昨收:").append(priceCloseAtYesterday)
                .append(", 当前:").append(price).append(", 最高:").append(priceMaxAtToday)
                .append(", :").append(priceMinAtToday).append("\n");
        str.append("  ").append("DealNumber: ").append(CommonUtil.formatLongWithComma(dealNumber)).append(", DealAmount: ").append(CommonUtil.formatLongWithComma(dealAmount));
        return str.toString();
    }

}
