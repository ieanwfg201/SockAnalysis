package com.hbhs.xb.sock.http;

import com.hbhs.xb.sock.analysis.http.HttpRequestUtils;
import org.junit.Test;

import java.util.Date;

/**
 * Created by walter.xu on 2016/12/22.
 */
public class HttpRequestUtilsTest {

    @Test
    public void testMain(){
        System.out.println(new Date().getTime());
    }

    @Test
    public void testLoadSinaWholeSock() throws Exception{
        String address = "http://hq.sinajs.cn/?rn="+new Date().getTime()+"&list=sh000001";
        String data = HttpRequestUtils.get(address, null, null);
        System.out.println(data);
    }
    @Test
    public void testLoadSinaPlateSockSummary(){
        String address = "http://vip.stock.finance.sina.com.cn/q/view/newSinaHy.php";
        String data = HttpRequestUtils.get(address, null, null);
        System.out.println(data);
    }
    @Test
    public void testLoadSinaWholeSockDetails(){
        String address = "";
        String data = HttpRequestUtils.get(address, null, null);
        System.out.println();
    }
}