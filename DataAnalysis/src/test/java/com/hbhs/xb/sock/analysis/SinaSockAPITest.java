package com.hbhs.xb.sock.analysis;

import com.hbhs.xb.sock.analysis.http.entity.SockMarketSummary;
import com.hbhs.xb.sock.analysis.http.entity.SockPlateSummary;
import com.hbhs.xb.sock.analysis.http.entity.SockSummary;
import com.hbhs.xb.sock.analysis.sockapi.SinaSockAPI;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by walter.xu on 2016/12/22.
 */
public class SinaSockAPITest {

    @Test
    public void testSockMarketSummary() throws Exception {
        SockMarketSummary summary = new SinaSockAPI().sockMarketSummary("sh000001");
        assertTrue(summary!=null);
        System.out.println(summary);
    }

    @Test
    public void testSockPlateSummary() throws Exception {
        List<SockPlateSummary> summary = new SinaSockAPI().sockPlateSummary();
        assertTrue(summary != null);
        summary.forEach(arg0 -> {
            System.out.println(arg0);
        });
    }

    @Test
    public void testSockSummary() throws Exception {
        List<SockSummary> list = new SinaSockAPI().sockSummary("new_dqhy", 10);
        assertTrue(list!=null);
        list.forEach(arg0->{
            System.out.println(arg0);
        });
    }
}