package com.hbhs.xb.sock.analysis.dao.impl;

import com.hbhs.xb.sock.analysis.dao.ISockMarketDao;
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

/**
 * Created by walter.xu on 2016/12/28.
 */
@Repository
public class SockMarketDao implements ISockMarketDao {
    @Autowired
    private MongoTemplate template;
    @Override
    public List<SockMarketMetrixEntity> querySockMarket(String marketID, Date startDate, Date endDate, List<String> fieldNameList) {
        if (fieldNameList==null||fieldNameList.size()==0) return null;


        Map<String, String> firstFieldMap = this.buildSockMarketFieldMap();
        Map<String, String> groupByFieldMap = this.buidlSockMarketGroupByFieldMap(fieldNameList);
        List<AggregationOperation> operations = new ArrayList<>();

        if (!StringUtils.isEmpty(marketID)||startDate!=null||endDate!=null){
            Criteria c = null;
            if (!StringUtils.isEmpty(marketID)){
                if (c==null) c =Criteria.where("sockMarketID").is(marketID);
                else c = c.and("sockMarketID").is(marketID);
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
//        AggregationResults<SockMarketMetrixEntity> results = template.aggregate(aggregation, "sock_market_summary", SockMarketMetrixEntity.class);
        AggregationResults<SockMarketMetrixEntity> results = template.aggregate(aggregation, "sock_market_summary",SockMarketMetrixEntity.class);
        return results.getMappedResults();
    }

    private Map<String, String> buildSockMarketFieldMap(){
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
    private  Map<String, String> buidlSockMarketGroupByFieldMap(List<String> selectFieldNameList){
        Map<String, String> fieldMap = new HashMap<>();
        selectFieldNameList.forEach(field -> {
            String replaceField = field.indexOf(".") > 0 ? field.substring(field.indexOf(".")+1) : field;
            fieldMap.put(field, replaceField);
        });
        return fieldMap;
    }
}
