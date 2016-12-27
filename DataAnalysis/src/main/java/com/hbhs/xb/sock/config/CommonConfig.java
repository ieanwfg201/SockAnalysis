package com.hbhs.xb.sock.config;

import com.hbhs.xb.sock.analysis.util.CommonUtil;
import com.hbhs.xb.sock.config.util.ScheduleConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;

/**
 * Created by walter.xu on 2016/12/23.
 */
@Configuration
@PropertySource(value = "classpath:schedule.properties")
@ComponentScan("com.hbhs.xb.sock.analysis")
@EnableScheduling
public class CommonConfig {
    @Resource
    protected Environment env;

    @Bean
    public ScheduleConfigBean scheduleConfigBean(){
        ScheduleConfigBean bean = new ScheduleConfigBean();
        // sock DATE
        bean.SOCK_LOADER_START_TIME = CommonUtil.praseInt(env.getProperty("cron.interface.loader.timer.start").replaceAll(":",""), 900);
        bean.SOCK_LOADER_END_TIME = CommonUtil.praseInt(env.getProperty("cron.interface.loader.timer.end").replaceAll(":", ""), 1530);
        if (!CommonUtil.isEmpty(env.getProperty("cron.interface.loader.cron"))) bean.SOCK_LOADER_CRON = env.getProperty("cron.interface.loader.cron");

        return bean;
    }
}
