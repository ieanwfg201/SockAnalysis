package com.hbhs.xb.sock.analysis.entity;

import com.hbhs.xb.sock.analysis.util.CommonUtil;

/**
 * Created by walter.xu on 2016/12/22.
 */
public class SockSummary {
    public String symbol;            // 标识，如sz/sh
    public String sockID;            // 股票id
    public String sockName;          // 名称
    public double price;             // 当前价格
    public double priceChange;       // 价格变动
    public double priceChangePersent;     // 价格变动百分比
    public double buyPrice;          // 买价
    public double sellPrice;         // 卖价
    public double priceCloseAtYesterday;    // 昨收
    public double priceOpenAtToday;         // 今开
    public double priceMaxAtToday;          // 今天最高
    public double priceMinAtToday;          // 今天最低
    public long dealNumber;                 // 成交量
    public long dealAmount;                 // 成交额
    public String time;                     // 当前价格时间，格式为：HH:mm:ss

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append(sockID).append("(").append(sockName).append("|").append(time).append(")").append(": dealNumber:")
                .append(CommonUtil.formatLongWithComma(dealNumber)).append(", dealAmount:").append(CommonUtil.formatLongWithComma(dealAmount)).append("\n");
        str.append("  |- ").append(price).append("(").append(priceChange).append("|").append(priceChangePersent).append("%|B=")
                .append(buyPrice).append("|S=").append(sellPrice).append(")").append(",  ")
                .append(price).append("(").append(priceCloseAtYesterday).append("|").append(priceOpenAtToday).append("|")
                .append(priceMaxAtToday).append("|").append(priceMinAtToday).append(")\n");
        return str.toString();
    }
}
