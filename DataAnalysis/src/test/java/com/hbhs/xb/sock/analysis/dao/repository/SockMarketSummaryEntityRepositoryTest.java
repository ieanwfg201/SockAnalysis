package com.hbhs.xb.sock.analysis.dao.repository;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbhs.xb.sock.BaseTest;
import com.hbhs.xb.sock.analysis.dao.entity.SockMarketSummaryEntity;
import com.hbhs.xb.sock.analysis.service.impl.SockMarketService;
import com.mongodb.BasicDBList;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by walter.xu on 2016/12/23.
 */
public class SockMarketSummaryEntityRepositoryTest extends BaseTest {

    @Autowired
    private SockMarketSummaryEntityRepository repository;
    @Autowired
    private MongoTemplate template ;
    @Test
    public void testCreate(){
        SockMarketSummaryEntity entity = new SockMarketSummaryEntity();
        entity.setCreateDate(new Date());
        entity.setCreateID(1);
        entity.setDay("20160101");
        entity.setTime("12:00:00");
        entity.setDealAmount(1000000);
        entity.setDealNumber(10000);
        entity.setPrice(10.0);
        entity.setPriceCloseAtYesterday(9.0);
        entity.setPriceOpenAtToday(9.5);
        entity.setPriceMaxAtToday(10.5);
        entity.setPriceMinAtToday(9.5);
        entity.setSockMarketID("sh01");
        entity.setSockMarketName("sockName");
        entity = repository.save(entity);
        System.out.println(entity);
    }

    @Test
    public void testQuery(){
        ObjectMapper mapper = new ObjectMapper();
        Iterable<SockMarketSummaryEntity> list = repository.findAll();
        list.forEach(a->{
            try {
                System.out.println(mapper.writeValueAsString(a));
            }catch (Exception e){}

        });
    }


    @Test
    public void testQueryByGroup(){
        GroupByResults<Data> list =  template.group("sock_market_summary", GroupBy.key("dateMetrix.day", "dateMetrix.hour")
                .initialDocument("{ count : 1}")
//                .reduceFunction("function(doc, prev) { prev.count += 1 }"), Data.class);
                .reduceFunction("function(doc, prev) { var min = prev.count; if(doc.count<min) min= prev.c }"), Data.class);
//                .reduceFunction("function(key, values) {if(values.length==0) return 0;var min = values[0];for (start = 1; start<values.length;start++){if(values[start] < min) min = values[start];}return min;}"), Data.class);
        if (list!=null){
            BasicDBList data = (BasicDBList)list.getRawResults().get("retval");
            for (Object o : data) {
                System.out.println(o);
            }
        }
    }
    public static class Data{
        private String year;
        private TimeEntity time;
        private long count;
        private double minPrice;
        private double maxPrice;
        public static class TimeEntity{
            private int day;
            private int hour;

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHour() {
                return hour;
            }

            public void setHour(int hour) {
                this.hour = hour;
            }
        }

        public double getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(double minPrice) {
            this.minPrice = minPrice;
        }

        public double getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(double maxPrice) {
            this.maxPrice = maxPrice;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }
        public String toString(){
            return "Year: "+year+", count: "+count;
        }
    }

    @Test
    public void testAggression(){
        String[] groupByField = new String[]{"dateMetrix.day", "dateMetrix.hour"};

        Aggregation aggregation = Aggregation.newAggregation(
                group(groupByField).min("price").as("minPrice").max("price").as("maxPrice")
                .first("dateMetrix.day").as("day").first("dateMetrix.hour").as("hour")
                .first("sockMarketID").as("sockMarketID")
                ,
                project("minPrice", "maxPrice","day","hour")//.and("year").previousOperation()
                .and("time").nested(bind("day","day").and("hour","hour"))
                ,
                sort(Sort.Direction.ASC, "day")
        );
        long start = System.currentTimeMillis();
        AggregationResults<Data> list = template.aggregate(aggregation, "sock_market_summary", Data.class);
        System.out.println("cost: "+(System.currentTimeMillis()-start));
        if (list!=null)
            System.out.println(list.getRawResults());
    }
}