package com.hbhs.xb.sock.analysis.service.impl;

import com.hbhs.xb.sock.analysis.dao.entity.SockDetailEntity;
import com.hbhs.xb.sock.analysis.dao.repository.SockDetailEntityRepository;
import com.hbhs.xb.sock.analysis.entity.SockSummary;
import com.hbhs.xb.sock.analysis.service.ISockDetailService;
import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by walter.xu on 2016/12/26.
 */
@Service
public class SockDetailService implements ISockDetailService {

    @Autowired
    private SockDetailEntityRepository sockDetailEntityRepository;

    @Override
    public void saveSockDetailList(List<SockSummary> summaryList){
        if (summaryList==null||summaryList.size()==0) return ;
        List<SockDetailEntity> entityList = new ArrayStack<>();
        summaryList.forEach(summary->{
            if (summary!=null) entityList.add(trans2Entity(summary));
        });
        sockDetailEntityRepository.save(entityList);
    }

    private SockDetailEntity trans2Entity(SockSummary summary){
        if (summary==null) return null;
        SockDetailEntity entity = new SockDetailEntity().sockSummary(summary);
        entity.setCreateDate(new Date());
        entity.setCreateID(1);
        return entity;
    }
}
