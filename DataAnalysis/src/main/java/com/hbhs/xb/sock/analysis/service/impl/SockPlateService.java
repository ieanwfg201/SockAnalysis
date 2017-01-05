package com.hbhs.xb.sock.analysis.service.impl;

import com.hbhs.xb.sock.analysis.dao.entity.SockPlateSummaryEntity;
import com.hbhs.xb.sock.analysis.dao.repository.SockPlateSummaryEntityRepository;
import com.hbhs.xb.sock.analysis.http.entity.SockPlateSummary;
import com.hbhs.xb.sock.analysis.service.ISockPlateService;
import com.hbhs.xb.sock.analysis.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by walter.xu on 2016/12/26.
 */
@Service
public class SockPlateService implements ISockPlateService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private ExecutorService service = Executors.newFixedThreadPool(1);
    @Autowired
    private SockPlateSummaryEntityRepository sockPlateSummaryEntityRepository;

    @Override
    public void saveSockPlateListAsyn(List<SockPlateSummary> summaryList){
        if (summaryList==null||summaryList.size()==0) return ;
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<SockPlateSummaryEntity> entityList = new ArrayList<>();
                    summaryList.forEach(summary -> {
                        if (summary != null) entityList.add(trans2Entity(summary));
                    });
                    sockPlateSummaryEntityRepository.save(entityList);
                } catch (Exception e) {
                    logger.error("Error to save sock-plate data into database: msg:", e);
                }
            }
            private SockPlateSummaryEntity trans2Entity(SockPlateSummary summary) {
                if (summary == null) return null;
                SockPlateSummaryEntity entity = new SockPlateSummaryEntity().sockPlateSummary(summary);
                entity.setCreateID(1);
                entity.setCreateDate(CommonUtil.getBeiJinDate(null));
                return entity;
            }
        });
    }


}
