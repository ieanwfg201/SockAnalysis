package com.hbhs.xb.sock.analysis.service.impl;

import com.hbhs.xb.sock.analysis.TimeTypeEnum;
import com.hbhs.xb.sock.analysis.dao.ISockMarketDao;
import com.hbhs.xb.sock.analysis.dao.entity.SockMarketSummaryEntity;
import com.hbhs.xb.sock.analysis.dao.repository.SockMarketSummaryEntityRepository;
import com.hbhs.xb.sock.analysis.dao.entity.SockMarketMetrixEntity;
import com.hbhs.xb.sock.analysis.http.entity.SockMarketSummary;
import com.hbhs.xb.sock.analysis.service.ISockMarketService;
import com.hbhs.xb.sock.analysis.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by walter.xu on 2016/12/26.
 */
@Service
public class SockMarketService implements ISockMarketService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private ExecutorService service = Executors.newFixedThreadPool(1);
    @Autowired
    private SockMarketSummaryEntityRepository sockMarketSummaryEntityRepository;
    @Autowired
    private ISockMarketDao sockMarketDao;

    @Override
    public void saveSockMarketAsyn(SockMarketSummary summary) {
        if (summary==null) return ;
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    SockMarketSummaryEntity entity = trans2Entity(summary);
                    sockMarketSummaryEntityRepository.save(entity);
                } catch (Exception e){
                    logger.error("Error to save sock-market data into database: msg:",e);
                }
            }
            private SockMarketSummaryEntity trans2Entity(SockMarketSummary summary){
                SockMarketSummaryEntity entity = new SockMarketSummaryEntity().sockMarketSummary(summary);
                entity.setCreateID(1);
                entity.setCreateDate(CommonUtil.getBeiJinDate(null));
                return entity;
            }
        });
    }

    @Override
    public List<SockMarketMetrixEntity> querySockMarket(String marketID, Date startDate, Date endDate, int type){
        return sockMarketDao.querySockMarket(marketID,startDate,endDate,TimeTypeEnum.generateFieldNames(type));
    }

}
