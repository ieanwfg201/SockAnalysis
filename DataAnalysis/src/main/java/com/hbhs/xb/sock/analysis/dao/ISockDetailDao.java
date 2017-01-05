package com.hbhs.xb.sock.analysis.dao;

import com.hbhs.xb.sock.analysis.dao.entity.SockDetailMetrixEntiy;

import java.util.Date;
import java.util.List;

/**
 * Created by walter.xu on 2017/1/2.
 */
public interface ISockDetailDao {
    List<SockDetailMetrixEntiy> querySockDetail(String plateID, String sockID, Date startDate,  Date endDate, List<String> fieldNameList);
}
