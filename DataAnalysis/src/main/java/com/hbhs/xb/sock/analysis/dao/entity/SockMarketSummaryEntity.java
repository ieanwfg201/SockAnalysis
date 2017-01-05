package com.hbhs.xb.sock.analysis.dao.entity;

import com.hbhs.xb.sock.analysis.http.entity.SockMarketSummary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by walter.xu on 2016/12/23.
 */
@Document(collection = "sock_market_summary")
public class SockMarketSummaryEntity implements Serializable {
    @Id
    private String id;                  // key
    private String sockMarketID;        // 股市id
    private String sockMarketName;      // 股市名称
    private double priceOpenAtToday;         // 今天开
    private double priceCloseAtYesterday;    // 昨天收
    private double priceMaxAtToday;          // 今天最高
    private double priceMinAtToday;          // 今天最低
    private double price;      // 当前点数
    private String day;                 // 时间：yyyy-MM-dd
    private String time;                // 时间：HH:mm:ss
    private long dealNumber;          // 成交量
    private long dealAmount;          // 成交额
    private DateMetrix dateMetrix = new DateMetrix();

    private Integer createID;
    private Date createDate;

    public SockMarketSummaryEntity sockMarketSummary(SockMarketSummary summary){
        if (summary!=null){
            this.sockMarketID = summary.sockMarketID;
            this.sockMarketName = summary.sockMarketName;
            this.priceOpenAtToday = summary.priceOpenAtToday;
            this.priceCloseAtYesterday = summary.priceCloseAtYesterday;
            this.priceMaxAtToday = summary.priceMaxAtToday;
            this.priceMinAtToday = summary.priceMinAtToday;
            this.price = summary.price;
            this.day = summary.day;
            this.time = summary.time;
            this.dealNumber = summary.dealNumber;
            this.dealAmount = summary.dealAmount;
        }
        return this;
    }

    public String getSockMarketID() {
        return sockMarketID;
    }

    public void setSockMarketID(String sockMarketID) {
        this.sockMarketID = sockMarketID;
    }

    public String getSockMarketName() {
        return sockMarketName;
    }

    public void setSockMarketName(String sockMarketName) {
        this.sockMarketName = sockMarketName;
    }

    public double getPriceOpenAtToday() {
        return priceOpenAtToday;
    }

    public void setPriceOpenAtToday(double priceOpenAtToday) {
        this.priceOpenAtToday = priceOpenAtToday;
    }

    public double getPriceCloseAtYesterday() {
        return priceCloseAtYesterday;
    }

    public void setPriceCloseAtYesterday(double priceCloseAtYesterday) {
        this.priceCloseAtYesterday = priceCloseAtYesterday;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
        this.createDate = createDate; dateMetrix.date(createDate);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DateMetrix getDateMetrix() {
        return dateMetrix;
    }

    public void setDateMetrix(DateMetrix dateMetrix) {
        this.dateMetrix = dateMetrix;
    }
}
