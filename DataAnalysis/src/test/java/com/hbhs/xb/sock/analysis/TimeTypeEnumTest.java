package com.hbhs.xb.sock.analysis;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by walter.xu on 2016/12/29.
 */
public class TimeTypeEnumTest {

    @Test
    public void testGenerateFieldNames(){
        List<String> list = TimeTypeEnum.generateFieldNames(1);
        System.out.println(Arrays.toString(list.toArray()));
        list = TimeTypeEnum.generateFieldNames(2);
        System.out.println(Arrays.toString(list.toArray()));
        list = TimeTypeEnum.generateFieldNames(3);
        System.out.println(Arrays.toString(list.toArray()));
        list = TimeTypeEnum.generateFieldNames(4);
        System.out.println(Arrays.toString(list.toArray()));
        list = TimeTypeEnum.generateFieldNames(5);
        System.out.println(Arrays.toString(list.toArray()));
        list = TimeTypeEnum.generateFieldNames(6);
        System.out.println(Arrays.toString(list.toArray()));
        list = TimeTypeEnum.generateFieldNames(7);
        System.out.println(Arrays.toString(list.toArray()));
        list = TimeTypeEnum.generateFieldNames(8);
        System.out.println(Arrays.toString(list.toArray()));
        list = TimeTypeEnum.generateFieldNames(9);
        System.out.println(Arrays.toString(list.toArray()));
        list = TimeTypeEnum.generateFieldNames(10);
        System.out.println(Arrays.toString(list.toArray()));

    }

}