package com.hbhs.xb.sock.analysis.dao.entity;

import com.hbhs.xb.sock.analysis.entity.SockSummary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by walter.xu on 2016/12/23.
 */
@Document(collection = "sock_detail")
public class SockDetailEntity implements Serializable {
    @Id
    private String id;
    private String symbol;            // 标识，如sz/sh
    private String sockID;            // 股票id
    private String sockName;          // 名称
    private double price;             // 当前价格
    private double priceChange;       // 价格变动
    private double priceChangePersent;     // 价格变动百分比
    private double buyPrice;          // 买价
    private double sellPrice;         // 卖价
    private double priceCloseAtYesterday;    // 昨收
    private double priceOpenAtToday;         // 今开
    private double priceMaxAtToday;          // 今天最高
    private double priceMinAtToday;          // 今天最低
    private long dealNumber;                 // 成交量
    private long dealAmount;                 // 成交额
    private String time;                     // 当前价格时间，格式为：HH:mm:ss

    private Integer createID;
    private Date createDate;

    public SockDetailEntity sockSummary(SockSummary summary){
        if (summary!=null){
            symbol = summary.symbol;
            sockID = summary.sockID;
            sockName = summary.sockName;
            price = summary.price;
            priceChange = summary.priceChange;
            priceChangePersent = summary.priceChangePersent;
            buyPrice = summary.buyPrice;
            sellPrice = summary.sellPrice;
            priceCloseAtYesterday = summary.priceCloseAtYesterday;
            priceOpenAtToday = summary.priceOpenAtToday;
            priceMaxAtToday = summary.priceMaxAtToday;
            priceMinAtToday = summary.priceMinAtToday;
            dealNumber = summary.dealNumber;
            dealAmount = summary.dealAmount;
            time = summary.time;
        }
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSockID() {
        return sockID;
    }

    public void setSockID(String sockID) {
        this.sockID = sockID;
    }

    public String getSockName() {
        return sockName;
    }

    public void setSockName(String sockName) {
        this.sockName = sockName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(double priceChange) {
        this.priceChange = priceChange;
    }

    public double getPriceChangePersent() {
        return priceChangePersent;
    }

    public void setPriceChangePersent(double priceChangePersent) {
        this.priceChangePersent = priceChangePersent;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getPriceCloseAtYesterday() {
        return priceCloseAtYesterday;
    }

    public void setPriceCloseAtYesterday(double priceCloseAtYesterday) {
        this.priceCloseAtYesterday = priceCloseAtYesterday;
    }

    public double getPriceOpenAtToday() {
        return priceOpenAtToday;
    }

    public void setPriceOpenAtToday(double priceOpenAtToday) {
        this.priceOpenAtToday = priceOpenAtToday;
    }

    public double getPriceMaxAtToday() {
        return priceMaxAtToday;
    }

    public void setPriceMaxAtToday(double priceMaxAtToday) {
        this.priceMaxAtToday = priceMaxAtToday;
    }

    public double getPriceMinAtToday() {
        return priceMinAtToday;
    }

    public void setPriceMinAtToday(double priceMinAtToday) {
        this.priceMinAtToday = priceMinAtToday;
    }

    public long getDealNumber() {
        return dealNumber;
    }

    public void setDealNumber(long dealNumber) {
        this.dealNumber = dealNumber;
    }

    public long getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(long dealAmount) {
        this.dealAmount = dealAmount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getCreateID() {
        return createID;
    }

    public void setCreateID(Integer createID) {
        this.createID = createID;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
