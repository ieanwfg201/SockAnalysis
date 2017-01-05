package com.hbhs.xb.sock.analysis.dao.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by walter.xu on 2016/12/28.
 */
public class DateMetrixTest {

    @Test
    public void testDateMetrix() throws Exception{
        DateMetrix a = new DateMetrix();
        a.date(new Date());
        System.out.println(new ObjectMapper().writeValueAsString(a));
    }
}