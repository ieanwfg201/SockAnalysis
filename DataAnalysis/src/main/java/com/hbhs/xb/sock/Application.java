package com.hbhs.xb.sock;

import com.hbhs.xb.sock.config.CommonConfig;
import com.hbhs.xb.sock.config.JdbcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by walter.xu on 2016/12/22.
 */
@SpringBootApplication
@Import(value = {JdbcConfig.class, CommonConfig.class})
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

}
