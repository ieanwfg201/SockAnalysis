package com.hbhs.xb.sock.analysis.service.impl;

import com.hbhs.xb.sock.analysis.dao.entity.SockDetailEntity;
import com.hbhs.xb.sock.analysis.dao.repository.SockDetailEntityRepository;
import com.hbhs.xb.sock.analysis.http.entity.SockSummary;
import com.hbhs.xb.sock.analysis.service.ISockDetailService;
import com.hbhs.xb.sock.analysis.util.CommonUtil;
import org.apache.tomcat.util.digester.ArrayStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by walter.xu on 2016/12/26.
 */
@Service
public class SockDetailService implements ISockDetailService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private ExecutorService service = Executors.newFixedThreadPool(10);
    @Autowired
    private SockDetailEntityRepository sockDetailEntityRepository;

    @Override
    public void saveSockDetailListAsyn(List<SockSummary> summaryList, String plateID){
        if (summaryList==null||summaryList.size()==0) return ;
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<SockDetailEntity> entityList = new ArrayStack<>();
                    summaryList.forEach(summary -> {
                        if (summary != null) entityList.add(trans2Entity(summary, plateID));
                    });
                    sockDetailEntityRepository.save(entityList);
                } catch (Exception e) {
                    logger.error("Error to save sock-detail data into database: msg:",e);
                }
            }

            private SockDetailEntity trans2Entity(SockSummary summary, String plateID) {
                if (summary == null) return null;
                SockDetailEntity entity = new SockDetailEntity().sockSummary(summary);
                entity.setPlateID(plateID);
                entity.setCreateDate(CommonUtil.getBeiJinDate(null));
                entity.setCreateID(1);
                return entity;
            }
        });
    }



}
