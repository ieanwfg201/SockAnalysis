package com.hbhs.xb.sock.analysis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by walter.xu on 2016/12/29.
 */
public enum TimeTypeEnum {
    DAY(1, "metrix.day"),
    HOUR(2, "metrix.hour"),
    MINUTE_30(3, "metrix.minute30"),
    MINUTE_15(4, "metrix.minute15"),
    MINUTE_10(5, "metrix.minute10"),
    MINUTE_5(6, "metrix.minute5"),
    MINUTE_1(7, "metrix.minute1"),
    SECOND_30(8, "metrix.second30"),
    SECOND_10(9, "metrix.second10");

    private Integer type;
    private String fieldName;
    TimeTypeEnum(int type, String fieldName){
        this.type = type;
        this.fieldName = fieldName;
    }
    public String getFieldName(){return this.fieldName; }

    public static List<String> generateFieldNames(int type){
        List<String> fieldList = new ArrayList<>();
        // day
        fieldList.add(DAY.getFieldName());
        // hour
        if (type!=1){
            fieldList.add(HOUR.getFieldName());
            // minute
            if (type!=2){
                switch (type){
                    case 3: fieldList.add(MINUTE_30.getFieldName()); break;
                    case 4: fieldList.add(MINUTE_15.getFieldName()); break;
                    case 5: fieldList.add(MINUTE_10.getFieldName()); break;
                    case 6: fieldList.add(MINUTE_5.getFieldName()); break;
                    default: fieldList.add(MINUTE_1.getFieldName()); break;
                }
                // seconds
                if (type!=3&&type!=4&&type!=5&&type!=6&&type!=7){
                    if (type == 8) fieldList.add(SECOND_30.getFieldName());
                    else if (type == 9) fieldList.add(SECOND_10.getFieldName());
                }
            }
        }
        return fieldList;
    }
}
