package com.hbhs.xb.sock.analysis.dao;

import com.hbhs.xb.sock.analysis.dao.entity.SockMarketMetrixEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by walter.xu on 2016/12/28.
 */
public interface ISockMarketDao {
    List<SockMarketMetrixEntity> querySockMarket(String marketID, Date startDate, Date endDate, List<String> fieldNameList);
}
