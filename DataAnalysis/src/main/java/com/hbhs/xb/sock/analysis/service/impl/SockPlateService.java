package com.hbhs.xb.sock.analysis.service.impl;

import com.hbhs.xb.sock.analysis.dao.entity.SockPlateSummaryEntity;
import com.hbhs.xb.sock.analysis.dao.repository.SockPlateSummaryEntityRepository;
import com.hbhs.xb.sock.analysis.entity.SockPlateSummary;
import com.hbhs.xb.sock.analysis.service.ISockPlateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by walter.xu on 2016/12/26.
 */
@Service
public class SockPlateService implements ISockPlateService {

    @Autowired
    private SockPlateSummaryEntityRepository sockPlateSummaryEntityRepository;

    @Override
    public void saveSockPlateList(List<SockPlateSummary> summaryList){
        if (summaryList==null||summaryList.size()==0) return ;
        List<SockPlateSummaryEntity> entityList = new ArrayList<>();
        summaryList.forEach(summary->{
            if (summary!=null) entityList.add(trans2Entity(summary));
        });
        sockPlateSummaryEntityRepository.save(entityList);
    }

    private SockPlateSummaryEntity trans2Entity(SockPlateSummary summary){
        if (summary==null) return null;
        SockPlateSummaryEntity entity = new SockPlateSummaryEntity().sockPlateSummary(summary);
        entity.setCreateID(1);
        entity.setCreateDate(new Date());
        return entity;
    }
}
