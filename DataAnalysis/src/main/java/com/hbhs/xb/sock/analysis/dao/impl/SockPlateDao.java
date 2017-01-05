package com.hbhs.xb.sock.analysis.dao.impl;

import com.hbhs.xb.sock.analysis.dao.ISockPlateDao;
import com.hbhs.xb.sock.analysis.dao.entity.SockPlateMetrixEntity;
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
 * Created by walter.xu on 2017/1/2.
 */
@Repository
public class SockPlateDao implements ISockPlateDao {
    @Autowired
    private MongoTemplate template;

    @Override
    public List<SockPlateMetrixEntity> querySockPlate(String plateID, Date startDate, Date endDate, List<String> fieldNameList){
        if (fieldNameList==null||fieldNameList.size()==0) return null;
        Map<String, String> firstFieldMap = this.buildSockPlateFieldMap();
        Map<String, String> groupByFieldMap = this.buidlSockPlateGroupByFieldMap(fieldNameList);
        List<AggregationOperation> operations = new ArrayList<>();
        // maching operation
        if (!StringUtils.isEmpty(plateID)||startDate!=null||endDate!=null){
            Criteria c = null;
            if (!StringUtils.isEmpty(plateID)){
                if (c==null) c =Criteria.where("plateID").is(plateID);
                else c = c.and("plateID").is(plateID);
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
        GroupOperation groupOperation = group(fieldNameList.toArray(new String[fieldNameList.size()]));
        for(Map.Entry<String, String> entry: firstFieldMap.entrySet()){
            groupOperation = groupOperation.first(entry.getKey()).as(entry.getValue());
        }
        for(Map.Entry<String, String> entry: groupByFieldMap.entrySet()){
            groupOperation = groupOperation.first(entry.getKey()).as(entry.getValue());
        }
        operations.add(groupOperation);
        // project bind operation
        Set<String> projectFieldSet = new HashSet<>();
        projectFieldSet.addAll(firstFieldMap.values());
        projectFieldSet.addAll(groupByFieldMap.values());
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
        AggregationResults<SockPlateMetrixEntity> results = template.aggregate(aggregation, "sock_plate_summary",SockPlateMetrixEntity.class);
        return results.getMappedResults();
    }

    private Map<String, String> buildSockPlateFieldMap(){
        Map<String, String> fieldMap = new TreeMap<>();
        fieldMap.put("plateID","plateID");
        fieldMap.put("plateName", "plateName");
        fieldMap.put("dealAmount", "dealAmount");
        fieldMap.put("dealNumber", "dealNumber");
        fieldMap.put("averagePrice", "averagePrice");
        fieldMap.put("priceChange","priceChange");
        fieldMap.put("priceChangePersent", "priceChangePersent");
        fieldMap.put("companyCount","companyCount");
        return fieldMap;
    }
    private  Map<String, String> buidlSockPlateGroupByFieldMap(List<String> selectFieldNameList){
        Map<String, String> fieldMap = new TreeMap<>();
        selectFieldNameList.forEach(field -> {
            String replaceField = field.indexOf(".") > 0 ? field.substring(field.indexOf(".") + 1) : field;
            fieldMap.put(field, replaceField);
        });
        return fieldMap;
    }
}
