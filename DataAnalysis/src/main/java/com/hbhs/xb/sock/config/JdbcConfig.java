package com.hbhs.xb.sock.config;

import com.hbhs.xb.sock.analysis.dao.repository.SockMarketSummaryEntityRepository;
import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by walter.xu on 2016/12/23.
 */
@Configuration
@PropertySource(value = "classpath:jdbc.properties")
@EnableTransactionManagement
@EnableMongoRepositories(basePackages = "com.hbhs.xb.sock.analysis.dao")
public class JdbcConfig {
    @Resource
    protected Environment env;

    @Bean
    public MongoTemplate mongoTemplate(){
        if (StringUtils.isEmpty(env.getProperty("mongo.host"))) throw new RuntimeException("No configure for 'mongo.host'");
        if (StringUtils.isEmpty(env.getProperty("mongo.port"))) throw new RuntimeException("No configure for 'mongo.port'");
        if (StringUtils.isEmpty(env.getProperty("mongo.database"))) throw new RuntimeException("No configure for 'mongo.database'");
        if (StringUtils.isEmpty(env.getProperty("mongo.username"))) throw new RuntimeException("No configure for 'mongo.username'");
        if (StringUtils.isEmpty(env.getProperty("mongo.password"))) throw new RuntimeException("No configure for 'mongo.password'");
        Mongo mongo = new Mongo(env.getProperty("mongo.host"), Integer.valueOf(env.getProperty("mongo.port")));
        return new MongoTemplate(mongo, env.getProperty("mongo.database"));
    }

}
