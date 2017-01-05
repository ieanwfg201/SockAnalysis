package com.hbhs.xb.sock.analysis.dao.impl;

import com.hbhs.xb.sock.BaseTest;
import com.hbhs.xb.sock.analysis.dao.ISockPlateDao;
import com.hbhs.xb.sock.analysis.dao.entity.SockPlateMetrixEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by walter.xu on 2017/1/2.
 */
public class SockPlateDaoTest extends BaseTest {
    @Autowired
    private ISockPlateDao sockPlateDao;

    @Test
    public void testQuerySockPlate() throws Exception {

        List<SockPlateMetrixEntity> list =sockPlateDao.querySockPlate(null,null,null, Arrays.asList("dateMetrix.day", "dateMetrix.hour"));
        list.forEach(data->{
            System.out.println(data);
        });
    }
}