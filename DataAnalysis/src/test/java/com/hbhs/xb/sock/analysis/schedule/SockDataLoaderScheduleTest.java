package com.hbhs.xb.sock.analysis.schedule;

import com.hbhs.xb.sock.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by walter.xu on 2016/12/26.
 */
public class SockDataLoaderScheduleTest extends BaseTest {
    @Autowired
    private SockDataLoaderSchedule schedule;
    @Test
    public void testRunTask() throws Exception {
        schedule.runTask();
        Thread.sleep(100000);
    }
}