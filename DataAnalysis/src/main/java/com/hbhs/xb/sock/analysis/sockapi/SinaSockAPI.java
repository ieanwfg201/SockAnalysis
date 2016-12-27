package com.hbhs.xb.sock.analysis.sockapi;

import com.hbhs.xb.sock.analysis.entity.SockMarketSummary;
import com.hbhs.xb.sock.analysis.entity.SockPlateSummary;
import com.hbhs.xb.sock.analysis.entity.SockSummary;
import com.hbhs.xb.sock.analysis.util.SockAPIUtil;
import com.hbhs.xb.sock.analysis.http.HttpRequestUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by walter.xu on 2016/12/22.
 */
@Component
public class SinaSockAPI implements SockAPI {

    @Override
    public SockMarketSummary sockMarketSummary(String sockMarketID){
        if (StringUtils.isEmpty(sockMarketID)) return null;
        String address = "http://hq.sinajs.cn/?rn="+new Date().getTime()+"&list=sh000001";
        String result = HttpRequestUtils.get(address, null, null);
        if (result!=null&&result.indexOf("\"")>0) result = result.substring(result.indexOf("\"")+1);
        return SockAPIUtil.transSinaSockMarketSummary(sockMarketID, result);
    }

    @Override
    public List<SockPlateSummary> sockPlateSummary(){
        String address = "http://vip.stock.finance.sina.com.cn/q/view/newSinaHy.php";
        String result = HttpRequestUtils.get(address, null, null);
        // 格式化：var S_Finance_bankuai_sinaindustry = {"new_blhy":"new_blhy,玻璃行业,19,20.362352941176,0.032941176470588,0.1620....
        if (result!=null&&result.indexOf("{")>0) result = result.substring(result.indexOf("{"));  // 仅返回json对象
        return SockAPIUtil.transSinaSockPlateSummary(result);
    }

    @Override
    public List<SockSummary> sockSummary(String plateID, int limit){
        if (StringUtils.isEmpty(plateID)) return null;
        if (limit == 0) limit = 10;
        String address = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num="+limit+"&sort=changepercent&asc=0&node="+plateID+"&symbol=&_s_r_a=sort";
        String result = HttpRequestUtils.get(address, null, null);
        // 返回的数据为json格式
        return SockAPIUtil.transSinaSockSummary(result);
    }

}
