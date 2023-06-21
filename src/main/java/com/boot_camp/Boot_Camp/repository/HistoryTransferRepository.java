package com.boot_camp.Boot_Camp.repository;

import com.boot_camp.Boot_Camp.model.entity.HistoryTransferEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HistoryTransferRepository extends ElasticsearchRepository<HistoryTransferEntity,String> {
    List<HistoryTransferEntity> findByAccountId(String id);
}

