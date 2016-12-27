package com.hbhs.xb.sock.analysis.service.impl;

import com.hbhs.xb.sock.analysis.dao.entity.SockMarketSummaryEntity;
import com.hbhs.xb.sock.analysis.dao.repository.SockMarketSummaryEntityRepository;
import com.hbhs.xb.sock.analysis.entity.SockMarketSummary;
import com.hbhs.xb.sock.analysis.schedule.SockDataLoaderStatusHolder;
import com.hbhs.xb.sock.analysis.service.ISockMarketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
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
                entity.setCreateDate(new Date());
                return entity;
            }
        });
    }


}
