package com.hbhs.xb.sock.analysis.schedule;

import com.hbhs.xb.sock.analysis.entity.SockMarketSummary;
import com.hbhs.xb.sock.analysis.entity.SockPlateSummary;
import com.hbhs.xb.sock.analysis.entity.SockSummary;
import com.hbhs.xb.sock.analysis.service.ISockDetailService;
import com.hbhs.xb.sock.analysis.service.ISockMarketService;
import com.hbhs.xb.sock.analysis.service.ISockPlateService;
import com.hbhs.xb.sock.analysis.sockapi.SockAPI;
import com.hbhs.xb.sock.config.util.ScheduleConfigBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by walter.xu on 2016/12/26.
 */
@Component
public class SockDataLoaderSchedule {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ISockMarketService sockMarketService;
    @Autowired
    private ISockPlateService sockPlateService;
    @Autowired
    private ISockDetailService sockDetailService;
    @Autowired
    private SockAPI sockAPI;
    @Autowired
    private ScheduleConfigBean configBean;
    private static String SOCK_MARKET = "sh000001";
    // 上次的执行的小时时间
    private static long LAST_TOTAL_DEAL_NUMBER = 0l;
    private static ExecutorService executor = Executors.newFixedThreadPool(20);
    private static Set<String> plateIDSet = new HashSet<>();

    @Scheduled(cron = "${cron.interface.loader.cron}")
    public void runTask(){
        // 校验当前时间是否需要load数据
        if (!configBean.isCurrentHourTimeIsBetweenConfiguredStartAndEndTime()){
            logger.warn("Current is not in data load time[{}:{}], ignore current data load action.", configBean.SOCK_LOADER_START_TIME, configBean.SOCK_LOADER_END_TIME);
            try{Thread.sleep(30*60*1000);}catch(Exception e){}
            return ;
        }
        saveSockMarketData();
        saveSockPlateData();
        saveSockDetailData();
    }

    private void saveSockMarketData(){
        new Thread(){
            @Override public void run(){
                try {
                    // 校验是否已经过了拉去数据的时间
                    SockMarketSummary marketSummary = sockAPI.sockMarketSummary(SOCK_MARKET);
                    if (marketSummary==null) {
                        return ;
                    }
                    if (!SockDataLoaderStatusHolder.setLastSockMarketDealNumber(marketSummary.dealNumber)) {
                        logger.warn("Old value. no need to load into database. deal number:{}", marketSummary.dealNumber);
                        return ;
                    }
                    // 保存market数据
                    sockMarketService.saveSockMarketAsyn(marketSummary);
                    logger.info("Success to load sock-market data into database at market: {}", SOCK_MARKET);
                } catch (Exception e) {
                    logger.error("Error to load sock-market data.", e);
                }
            }
        }.start();
    }


    private void saveSockPlateData(){
        if (SockDataLoaderStatusHolder.isSockMarketStop()){
            logger.debug("Sock market already closed, no need load sock-plate data anymore.");
            return ;
        }
        new Thread(){
            @Override public void run(){
                try {
                    List<SockPlateSummary> summaryList = sockAPI.sockPlateSummary();
                    if (summaryList!=null&&summaryList.size()>0){
                        summaryList.forEach(summary -> {
                            plateIDSet.add(summary.plateID);
                        });
                        sockPlateService.saveSockPlateListAsyn(summaryList);
                    }
                    logger.info("Success to load sock-plate data into database, total plate size: {}", summaryList.size());
                } catch (Exception e) {
                    logger.error("Error to load plate data.", e);
                }
            }
        }.start();
    }

    private void saveSockDetailData(){
        if (SockDataLoaderStatusHolder.isSockMarketStop()){
            logger.debug("Sock market already closed, no need load sock-detail data anymore.");
            return ;
        }
        if (plateIDSet.size()>0){
            plateIDSet.forEach(plateID->{
                new Thread(){
                    @Override
                    public void run(){
                        try {
                            List<SockSummary> detailList = sockAPI.sockSummary(plateID, 10);
                            sockDetailService.saveSockDetailListAsyn(detailList, plateID);
                            logger.debug("Success loading sock details data into database  plate: {}, total size:{}",
                                    plateID, detailList.size());
                        } catch (Exception e) {
                            logger.error("Error to save sock details into database.", e);
                        }
                    }
                }.start();
            });
        }
    }

}
