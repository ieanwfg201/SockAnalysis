package com.hbhs.xb.sock.analysis.dao.repository;

import com.hbhs.xb.sock.analysis.dao.entity.SockMarketSummaryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * Created by walter.xu on 2016/12/23.
 */
public interface SockMarketSummaryEntityRepository extends PagingAndSortingRepository<SockMarketSummaryEntity, String>,QueryByExampleExecutor<SockMarketSummaryEntity> {
}
