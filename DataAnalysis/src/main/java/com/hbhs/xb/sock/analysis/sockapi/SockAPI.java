package com.hbhs.xb.sock.analysis.sockapi;

import com.hbhs.xb.sock.analysis.http.entity.SockMarketSummary;
import com.hbhs.xb.sock.analysis.http.entity.SockPlateSummary;
import com.hbhs.xb.sock.analysis.http.entity.SockSummary;

import java.util.List;

/**
 * Created by walter.xu on 2016/12/22.
 */
public interface SockAPI {
    SockMarketSummary sockMarketSummary(String sockMarketID);

    List<SockPlateSummary> sockPlateSummary();

    List<SockSummary> sockSummary(String plateID, int limit);
}
