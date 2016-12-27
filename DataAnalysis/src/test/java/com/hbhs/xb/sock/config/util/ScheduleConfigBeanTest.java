package com.hbhs.xb.sock.config.util;

import org.junit.Test;

import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * Created by walter.xu on 2016/12/27.
 */
public class ScheduleConfigBeanTest {

    @Test
    public void testIsCurrentHourTimeIsBetweenConfiguredStartAndEndTime() throws Exception {
        new ScheduleConfigBean().isCurrentHourTimeIsBetweenConfiguredStartAndEndTime();
    }
}