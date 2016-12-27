package com.hbhs.xb.sock.analysis;

/**
 * Created by walter.xu on 2016/12/23.
 */
public enum RateUnitEnum {

    SECOND(1, "second"),
    MINUTE(2, "minute"),
    HOUR(3, "hour");

    private int value;
    private String name;
    RateUnitEnum(int value, String name){
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static RateUnitEnum findRateUnit(String name){
        for(RateUnitEnum current: RateUnitEnum.values()){
            if (name.equalsIgnoreCase(current.getName()))
                return current;
        }
        return SECOND;
    }
}
