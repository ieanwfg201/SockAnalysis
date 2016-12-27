package com.hbhs.xb.sock;

import com.hbhs.xb.sock.analysis.dao.entity.SockMarketSummaryEntity;
import com.hbhs.xb.sock.analysis.dao.repository.SockMarketSummaryEntityRepository;
import com.hbhs.xb.sock.config.CommonConfig;
import com.hbhs.xb.sock.config.JdbcConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by walter.xu on 2016/12/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Import(value = {JdbcConfig.class, CommonConfig.class})
public class BaseTest {


}
