package com.hbhs.xb.sock.analysis.dao.impl;

import com.hbhs.xb.sock.BaseTest;
import com.hbhs.xb.sock.analysis.dao.ISockDetailDao;
import com.hbhs.xb.sock.analysis.dao.entity.SockDetailMetrixEntiy;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by walter.xu on 2017/1/3.
 */
public class SockDetailDaoTest extends BaseTest {
    @Autowired
    private ISockDetailDao sockDetailDao;

    @Test
    public void testQuerySockDetail() throws Exception {
        List<SockDetailMetrixEntiy> list = sockDetailDao.querySockDetail(null, null, null, null, Arrays.asList("dateMetrix.day", "dateMetrix.hour"));
        list.forEach(data->{
            System.out.println(data);
        });
    }
}