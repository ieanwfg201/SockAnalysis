package com.hbhs.xb.sock.analysis.dao.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbhs.xb.sock.BaseTest;
import com.hbhs.xb.sock.analysis.dao.entity.SockMarketSummaryEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by walter.xu on 2016/12/23.
 */
public class SockMarketSummaryEntityRepositoryTest extends BaseTest {

    @Autowired
    private SockMarketSummaryEntityRepository repository;
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
}