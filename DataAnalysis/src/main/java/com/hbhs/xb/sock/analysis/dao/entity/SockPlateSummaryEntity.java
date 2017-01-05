package com.hbhs.xb.sock.analysis.dao.entity;

import com.hbhs.xb.sock.analysis.http.entity.SockPlateSummary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by walter.xu on 2016/12/23.
 */
@Document(collection = "sock_plate_summary")
public class SockPlateSummaryEntity implements Serializable {
    @Id
    private String id;
    private String plateID;
    private String plateName;
    private Integer companyCount;
    private double averagePrice;
    private double priceChange;
    private double priceChangePersent;
    private long dealNumber;
    private long dealAmount;
    private String aheadSockID;
    private String aheadSockName;
    private double aheadSockPrice;
    private double aheadSockPriceChange;
    private double aheadSockChangePersent;
    private DateMetrix dateMetrix = new DateMetrix();

    private Integer createID;
    private Date createDate;

    public SockPlateSummaryEntity sockPlateSummary(SockPlateSummary summary){
        if (summary!=null){
            plateID = summary.plateID;
            plateName = summary.plateName;
            companyCount = summary.companyCount;
            averagePrice = summary.averagePrice;
            priceChange = summary.priceChange;
            priceChangePersent = summary.priceChangePersent;
            dealNumber = summary.dealNumber;
            dealAmount = summary.dealAmount;
            aheadSockID = summary.aheadSockID;
            aheadSockName = summary.aheadSockName;
            aheadSockPrice = summary.aheadSockPrice;
            aheadSockPriceChange = summary.aheadSockPriceChange;
            aheadSockChangePersent = summary.aheadSockChangePersent;
        }
        return this;
    }

    public String getPlateID() {
        return plateID;
    }

    public void setPlateID(String plateID) {
        this.plateID = plateID;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public Integer getCompanyCount() {
        return companyCount;
    }

    public void setCompanyCount(Integer companyCount) {
        this.companyCount = companyCount;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
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

    public String getAheadSockID() {
        return aheadSockID;
    }

    public void setAheadSockID(String aheadSockID) {
        this.aheadSockID = aheadSockID;
    }

    public String getAheadSockName() {
        return aheadSockName;
    }

    public void setAheadSockName(String aheadSockName) {
        this.aheadSockName = aheadSockName;
    }

    public double getAheadSockPrice() {
        return aheadSockPrice;
    }

    public void setAheadSockPrice(double aheadSockPrice) {
        this.aheadSockPrice = aheadSockPrice;
    }

    public double getAheadSockPriceChange() {
        return aheadSockPriceChange;
    }

    public void setAheadSockPriceChange(double aheadSockPriceChange) {
        this.aheadSockPriceChange = aheadSockPriceChange;
    }

    public double getAheadSockChangePersent() {
        return aheadSockChangePersent;
    }

    public void setAheadSockChangePersent(double aheadSockChangePersent) {
        this.aheadSockChangePersent = aheadSockChangePersent;
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
