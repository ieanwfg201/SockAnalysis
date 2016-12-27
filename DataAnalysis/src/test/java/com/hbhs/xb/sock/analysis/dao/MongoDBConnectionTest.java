package com.hbhs.xb.sock.analysis.dao;

import com.hbhs.xb.sock.BaseTest;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;
import org.junit.Test;

/**
 * Created by walter.xu on 2016/12/23.
 */
public class MongoDBConnectionTest extends BaseTest {
    @Test
    public void testMongoDBConnection(){
        ServerAddress address = new ServerAddress("192.168.99.100",55555);
        Mongo mongo = new Mongo(address);
//        mongo.getReadPreference().
    }
}
