package com.hbhs.xb.sock.analysis.service;

import com.hbhs.xb.sock.analysis.entity.SockPlateSummary;

import java.util.List;

/**
 * Created by walter.xu on 2016/12/26.
 */
public interface ISockPlateService {
    void saveSockPlateListAsyn(List<SockPlateSummary> summaryList);
}
