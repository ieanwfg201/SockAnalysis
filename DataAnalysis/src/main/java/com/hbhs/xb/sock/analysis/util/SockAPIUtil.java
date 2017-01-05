package com.hbhs.xb.sock.analysis.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbhs.xb.sock.analysis.http.entity.SockMarketSummary;
import com.hbhs.xb.sock.analysis.http.entity.SockPlateSummary;
import com.hbhs.xb.sock.analysis.http.entity.SockSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by walter.xu on 2016/12/22.
 */
public class SockAPIUtil {
    private static final Logger logger = LoggerFactory.getLogger(SockAPIUtil.class);
    /**
     * 结果串为：上证指数,3132.1558,3137.4297,3141.0881,3143.1685,3126.8872,0,0,145330200,160613253925,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2016-12-22,14:35:47,00
     * 对应为：名称/今开/昨收/当前价格/今天最高/今天最低/-/-/交易量/交易额/---/当天/时间/-
     * @param line format: 上证指数,3132.1558,3137.4297,3141.0881,3143.1685,3126.8872,0,0,145330200,160613253925,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2016-12-22,14:35:47,00;
     * @return
     */
    public static SockMarketSummary transSinaSockMarketSummary(String sockMarketID, String line){
        if (StringUtils.isEmpty(line)) return null;
        String[] args = line.split(",");
        if (args.length<12) return null;
        SockMarketSummary summary = new SockMarketSummary();
        summary.sockMarketID = sockMarketID;
        summary.sockMarketName = args[0];
        summary.priceOpenAtToday = CommonUtil.praseDouble(args[1], 0.0);
        summary.priceCloseAtYesterday = CommonUtil.praseDouble(args[2], 0.0);
        summary.price = CommonUtil.praseDouble(args[3], 0.0);
        summary.priceMaxAtToday = CommonUtil.praseDouble(args[4], 0.0);
        summary.priceMinAtToday = CommonUtil.praseDouble(args[5], 0.0);
        summary.dealNumber = CommonUtil.praseLong(args[8], 0);
        summary.dealAmount = CommonUtil.praseLong(args[9], 0);
        summary.day = args[args.length-3];
        summary.time = args[args.length-2];
        return summary;
    }

    public static List<SockPlateSummary> transSinaSockPlateSummary(String line){
        if (line==null||!line.startsWith("{")) return null;
        List<SockPlateSummary> summaryList = new ArrayList<>();
        try {
            Map<String, String> map = new ObjectMapper().readValue(line, Map.class);
            map.values().forEach(arg0->{
                String[] detailsArray = arg0.replaceAll("\"", "").split(",");
                if (detailsArray.length>12){
                    SockPlateSummary summary = new SockPlateSummary();
                    summary.plateID = detailsArray[0];
                    summary.plateName = detailsArray[1];
                    summary.companyCount = CommonUtil.praseInt(detailsArray[2], 0);
                    summary.averagePrice = CommonUtil.praseDouble(detailsArray[3], 0);
                    summary.priceChange = CommonUtil.praseDouble(detailsArray[4], 0);
                    summary.priceChangePersent = CommonUtil.praseDouble(detailsArray[5], 0);
                    summary.dealNumber = CommonUtil.praseLong(detailsArray[6], 0);
                    summary.dealAmount = CommonUtil.praseLong(detailsArray[7], 0);
                    summary.aheadSockID = detailsArray[8];
                    summary.aheadSockChangePersent = CommonUtil.praseDouble(detailsArray[9], 0);
                    summary.aheadSockPrice = CommonUtil.praseDouble(detailsArray[10], 0);
                    summary.aheadSockPriceChange = CommonUtil.praseDouble(detailsArray[11], 0);
                    summary.aheadSockName = detailsArray[12];
                    summaryList.add(summary);

                }
            });
        }catch (Exception e){
            logger.error("Error to prase data line to json: "+line, e);
        }

        return summaryList;
    }

    public static List<SockSummary> transSinaSockSummary(String line){
        if (StringUtils.isEmpty(line)||!line.startsWith("[")) return null;
        List<SockSummary> summaryList = new ArrayList<>();
        try {
            int startIndex = line.indexOf("{");
            int endIndex = line.indexOf("}");
            while(endIndex>startIndex){
                String targetLine = line.substring(startIndex, endIndex + 1);
                targetLine = targetLine.replaceAll("\\{","").replaceAll("\\}","").replaceAll("\"", "");
                SockSummary summary = new SockSummary();
                String[] arrays = targetLine.split(",");
                for(String field: arrays){
                    String[] keyValues = field.split(":",2);
                    if (keyValues.length==2){
                        switch (keyValues[0].trim()){
                            case "symbol": summary.sockID = keyValues[1]; break;
                            case "name": summary.sockName = keyValues[1]; break;
                            case "trade": summary.price = CommonUtil.praseDouble(keyValues[1], 0); break;
                            case "pricechange": summary.priceChange = CommonUtil.praseDouble(keyValues[1], 0); break;
                            case "changepercent": summary.priceChangePersent = CommonUtil.praseDouble(keyValues[1], 0); break;
                            case "buy": summary.buyPrice = CommonUtil.praseDouble(keyValues[1], 0); break;
                            case "sell": summary.sellPrice = CommonUtil.praseDouble(keyValues[1], 0); break;
                            case "settlement": summary.priceCloseAtYesterday = CommonUtil.praseDouble(keyValues[1], 0); break;
                            case "open": summary.priceOpenAtToday = CommonUtil.praseDouble(keyValues[1], 0); break;
                            case "high": summary.priceMaxAtToday = CommonUtil.praseDouble(keyValues[1], 0); break;
                            case "low": summary.priceMinAtToday = CommonUtil.praseDouble(keyValues[1], 0); break;
                            case "volume": summary.dealNumber = CommonUtil.praseLong(keyValues[1], 0); break;
                            case "amount": summary.dealAmount = CommonUtil.praseLong(keyValues[1], 0); break;
                            case "ticktime": summary.time = keyValues[1]; break;
                        }
                    }
                }
                summaryList.add(summary);
                startIndex = line.indexOf("{", endIndex+1);
                endIndex = line.indexOf("}", endIndex+1);
            }
        }catch (Exception e){
            logger.error("Error to prase data line to json: "+line, e);
        }
        return summaryList;
    }
}
