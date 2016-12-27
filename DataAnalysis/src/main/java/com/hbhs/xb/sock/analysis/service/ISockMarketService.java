package com.hbhs.xb.sock.analysis.service;

import com.hbhs.xb.sock.analysis.entity.SockMarketSummary;

import java.util.List;

/**
 * Created by walter.xu on 2016/12/26.
 */
public interface ISockMarketService {
    void saveSockMarket(SockMarketSummary summary);
    void saveSockMarketList(List<SockMarketSummary> summaryList);
}
