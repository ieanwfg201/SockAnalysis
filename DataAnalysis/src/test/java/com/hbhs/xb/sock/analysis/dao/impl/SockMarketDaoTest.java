package com.hbhs.xb.sock.analysis.dao.impl;

import com.hbhs.xb.sock.BaseTest;
import com.hbhs.xb.sock.analysis.dao.ISockMarketDao;
import com.hbhs.xb.sock.analysis.dao.entity.SockMarketMetrixEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by walter.xu on 2016/12/29.
 */
public class SockMarketDaoTest extends BaseTest {
    @Autowired
    private ISockMarketDao sockMarketDao;
    @Test
    public void testQuerySockMarket() throws Exception {
        List<SockMarketMetrixEntity> list = sockMarketDao.querySockMarket(null,null,null, Arrays.asList("dateMetrix.day", "dateMetrix.hour"));
        list.forEach(data->{
            System.out.println(data);
        });
    }
}