package com.hbhs.xb.sock.analysis.dao;

import com.hbhs.xb.sock.analysis.dao.entity.SockPlateMetrixEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by walter.xu on 2016/12/31.
 */
public interface ISockPlateDao {
    List<SockPlateMetrixEntity> querySockPlate(String plateID, Date startDate, Date endDate, List<String> fieldNameList);

}
