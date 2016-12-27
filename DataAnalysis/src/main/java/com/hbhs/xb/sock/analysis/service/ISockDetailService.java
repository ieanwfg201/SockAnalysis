package com.hbhs.xb.sock.analysis.service;

import com.hbhs.xb.sock.analysis.entity.SockSummary;

import java.util.List;

/**
 * Created by walter.xu on 2016/12/26.
 */
public interface ISockDetailService {

    void saveSockDetailListAsyn(List<SockSummary> summaryList, String plateID);
}
