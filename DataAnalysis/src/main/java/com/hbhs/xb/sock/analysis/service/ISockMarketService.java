package com.hbhs.xb.sock.analysis.service;

import com.hbhs.xb.sock.analysis.dao.entity.SockMarketMetrixEntity;
import com.hbhs.xb.sock.analysis.http.entity.SockMarketSummary;

import java.util.Date;
import java.util.List;

/**
 * Created by walter.xu on 2016/12/26.
 */
public interface ISockMarketService {
    void saveSockMarketAsyn(SockMarketSummary summary);

    List<SockMarketMetrixEntity> querySockMarket(String marketID, Date startDate, Date endDate, int type);
}
