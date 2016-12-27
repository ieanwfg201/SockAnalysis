package com.hbhs.xb.sock.analysis.service.impl;

import com.hbhs.xb.sock.analysis.dao.entity.SockMarketSummaryEntity;
import com.hbhs.xb.sock.analysis.dao.repository.SockMarketSummaryEntityRepository;
import com.hbhs.xb.sock.analysis.entity.SockMarketSummary;
import com.hbhs.xb.sock.analysis.service.ISockMarketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by walter.xu on 2016/12/26.
 */
@Service
public class SockMarketService implements ISockMarketService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SockMarketSummaryEntityRepository sockMarketSummaryEntityRepository;

    @Override
    public void saveSockMarket(SockMarketSummary summary) {
        if (summary==null) return ;
        SockMarketSummaryEntity entity = this.trans2Entity(summary);
        sockMarketSummaryEntityRepository.save(entity);

    }

    private SockMarketSummaryEntity trans2Entity(SockMarketSummary summary){
        SockMarketSummaryEntity entity = new SockMarketSummaryEntity().sockMarketSummary(summary);
        entity.setCreateID(1);
        entity.setCreateDate(new Date());
        return entity;
    }

    @Override
    public void saveSockMarketList(List<SockMarketSummary> summaryList){
        if (summaryList==null||summaryList.size()==0) return ;
        List<SockMarketSummaryEntity> entityList = new ArrayList<>();
        summaryList.forEach(summary->{
            entityList.add(trans2Entity(summary));
        });
        sockMarketSummaryEntityRepository.save(entityList);
    }
}
