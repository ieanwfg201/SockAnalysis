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

import java.util.List;

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
    // 上次的执行的小时时间
    private static String LAST_MARKET_LOAD_HOUR_MINUTE = "";

    @Scheduled(cron = "${cron.interface.loader.cron}")
    public void runTask(){
        try {
            // 校验当前时间是否需要load数据
            if (!configBean.isCurrentHourTimeIsBetweenConfiguredStartAndEndTime()){
                logger.debug("Current is not in data load time[{}:{}], ignore current data load action.",configBean.SOCK_LOADER_START_TIME,configBean.SOCK_LOADER_END_TIME);
                Thread.sleep(30*60*1000);
                return ;
            }
            // 校验是否已经过了拉去数据的时间
            SockMarketSummary marketSummary = sockAPI.sockMarketSummary("sh000001");
            String currentDayAndTime = formatDayAndTime(marketSummary);
            if (currentDayAndTime.equals("")||currentDayAndTime.equals(LAST_MARKET_LOAD_HOUR_MINUTE)){
                logger.debug("Old value. no need to load into database. current/last time:{}", currentDayAndTime);
                return ;
            }
            // 保存market数据
            sockMarketService.saveSockMarket(marketSummary);
            logger.debug("Success loading sock market data into database at time:{}", currentDayAndTime);
            // 保存plate数据
            List<SockPlateSummary> summaryList = sockAPI.sockPlateSummary();
            sockPlateService.saveSockPlateList(summaryList);
            logger.debug("Success loading sock plate data into database at time:{}, total size:{}",currentDayAndTime, summaryList.size());
            // 保存detail数据
            summaryList.forEach(summary->{
                try {
                    List<SockSummary> detailList = sockAPI.sockSummary(summary.plateID, 20);
                    sockDetailService.saveSockDetailList(detailList);
                    logger.debug("Success loading sock details data into database at time:{}, plate: {}.{}, total size:{}", currentDayAndTime,
                            summary.plateID, summary.plateName, detailList.size());
                } catch (Exception e){
                    logger.error("Error to save sock details into database.", e);
                }
            });

            LAST_MARKET_LOAD_HOUR_MINUTE = currentDayAndTime;

            logger.info("Success to load data into database at time: {}", currentDayAndTime);
        } catch (Exception e) {
            logger.error("Error to load plate data.", e);
        }
    }

    private String formatDayAndTime(SockMarketSummary summary){
        if (summary==null) return "";
        return summary.day+" "+summary.time;
    }


}
