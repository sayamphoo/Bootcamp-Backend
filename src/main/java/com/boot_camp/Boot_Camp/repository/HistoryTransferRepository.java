package com.boot_camp.Boot_Camp.repository;

import com.boot_camp.Boot_Camp.model.entity.HistoryTransferEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface HistoryTransferRepository extends ElasticsearchRepository<HistoryTransferEntity,String> {
    List<HistoryTransferEntity> findByMemberID(String id);
}
