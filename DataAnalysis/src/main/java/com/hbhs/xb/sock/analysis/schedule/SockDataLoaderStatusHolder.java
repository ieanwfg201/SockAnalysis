package com.hbhs.xb.sock.analysis.schedule;

/**
 * Created by walter.xu on 2016/12/27.
 */
public class SockDataLoaderStatusHolder {
    private transient static boolean isSockMarketStop = true;
    private transient static long lastSockMarketDealNumber = 0;
    private static Object SYN_OBJECT = new Object();

    public static boolean isSockMarketStop(){
        return isSockMarketStop;
    }
    public static boolean setLastSockMarketDealNumber(long lastSockMarketDealNumber){
        synchronized (SYN_OBJECT){
            if (SockDataLoaderStatusHolder.lastSockMarketDealNumber == lastSockMarketDealNumber) {
                isSockMarketStop = true;
                return false;
            }
            SockDataLoaderStatusHolder.lastSockMarketDealNumber = lastSockMarketDealNumber;
            isSockMarketStop = false;
            return true;
        }
    }

}
