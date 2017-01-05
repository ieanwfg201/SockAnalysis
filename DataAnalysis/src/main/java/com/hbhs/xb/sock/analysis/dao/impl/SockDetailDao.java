package com.hbhs.xb.sock.analysis.dao.impl;

import com.hbhs.xb.sock.analysis.dao.ISockDetailDao;
import com.hbhs.xb.sock.analysis.dao.entity.SockDetailMetrixEntiy;
import com.hbhs.xb.sock.analysis.dao.entity.SockMarketMetrixEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

/**
 * Created by walter.xu on 2017/1/3.
 */
@Repository
public class SockDetailDao implements ISockDetailDao {
    @Autowired
    private MongoTemplate template;

    @Override
    public List<SockDetailMetrixEntiy> querySockDetail(String plateID, String sockID, Date startDate,  Date endDate, List<String> fieldNameList){
        if (fieldNameList==null||fieldNameList.size()==0) return null;

        Map<String, String> firstFieldMap = this.buildSockDetailFieldMap();
        Map<String, String> groupByFieldMap = this.buidlSockDetailGroupByFieldMap(fieldNameList);
        List<AggregationOperation> operations = new ArrayList<>();

        if (!StringUtils.isEmpty(plateID)||!StringUtils.isEmpty(sockID)||startDate!=null||endDate!=null){
            Criteria c = null;
            if (!StringUtils.isEmpty(plateID)){
                c =Criteria.where("plateID").is(plateID);
            }
            if (!StringUtils.isEmpty(sockID)){
                if (c==null) c = Criteria.where("sockID").is(sockID);
                else c = c.and("sockID").is(sockID);
            }
            if (startDate!=null){
                if (c==null) c =Criteria.where("createDate").gte(startDate);
                else c = c.and("createDate").gte(startDate);
            }
            if (endDate!=null){
                if (c==null) c =Criteria.where("createDate").lte(endDate);
                else c = c.and("createDate").lte(endDate);
            }
            MatchOperation matchOperation = match(c);
            operations.add(matchOperation);
        }

        // group by operation
        GroupOperation groupOperation = group(fieldNameList.toArray(new String[fieldNameList.size()]))
                .min("price").as("priceMinAtCurrentTag").max("price").as("priceMaxAtCurrentTag");
        for(Map.Entry<String, String> entry: firstFieldMap.entrySet()){
            groupOperation = groupOperation.first(entry.getKey()).as(entry.getValue());
        }
        for(Map.Entry<String, String> entry: groupByFieldMap.entrySet()){
            groupOperation = groupOperation.first(entry.getKey()).as(entry.getValue());
        }
        operations.add(groupOperation);

        Set<String> projectFieldSet = new HashSet<>();
        projectFieldSet.addAll(firstFieldMap.values());
        projectFieldSet.addAll(groupByFieldMap.values());
        projectFieldSet.add("priceMinAtCurrentTag"); projectFieldSet.add("priceMaxAtCurrentTag");
        // project bind operation
        ProjectionOperation projectionOperation = project(projectFieldSet.toArray(new String[projectFieldSet.size()]));
        Fields nestedFields = null;
        for(Map.Entry<String, String> entry: groupByFieldMap.entrySet()){
            if (nestedFields == null) nestedFields = bind(entry.getValue(), entry.getValue());
            else nestedFields = nestedFields.and(entry.getValue(), entry.getValue());
        }
        projectionOperation = projectionOperation.and("date").nested(nestedFields);
        operations.add(projectionOperation);
        // sort
        SortOperation sortOperation = sort(Sort.Direction.ASC, groupByFieldMap.values().toArray(new String[groupByFieldMap.values().size()]));
//        SortOperation sortOperation = sort(Sort.Direction.ASC, "day","hour");
        operations.add(sortOperation);

        Aggregation aggregation = Aggregation.newAggregation(
                operations.toArray(new AggregationOperation[operations.size()])
        );
//        AggregationResults<SockDetailMetrixEntiy> results = template.aggregate(aggregation, "sock_detail", SockDetailMetrixEntiy.class);
        AggregationResults<SockDetailMetrixEntiy> results = template.aggregate(aggregation, "sock_detail",SockDetailMetrixEntiy.class);
        return results.getMappedResults();
    }

    private Map<String, String> buildSockDetailFieldMap(){
        Map<String, String> fieldMap = new TreeMap<>();
        fieldMap.put("sockMarketID","sockMarketID");
        fieldMap.put("sockMarketName", "sockMarketName");
        fieldMap.put("dealAmount", "dealAmount");
        fieldMap.put("dealNumber", "dealNumber");
        fieldMap.put("priceCloseAtYesterday", "priceCloseAtYesterday");
        fieldMap.put("priceOpenAtToday","priceOpenAtToday");
        fieldMap.put("priceMaxAtToday", "priceMaxAtToday");
        fieldMap.put("priceMinAtToday","priceMinAtToday");
        fieldMap.put("price", "price");
        return fieldMap;
    }
    private  Map<String, String> buidlSockDetailGroupByFieldMap(List<String> selectFieldNameList){
        Map<String, String> fieldMap = new HashMap<>();
        selectFieldNameList.forEach(field -> {
            String replaceField = field.indexOf(".") > 0 ? field.substring(field.indexOf(".")+1) : field;
            fieldMap.put(field, replaceField);
        });
        return fieldMap;
    }
}
