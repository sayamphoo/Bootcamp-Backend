package com.boot_camp.Boot_Camp.repository;

import com.boot_camp.Boot_Camp.model.entity.StoreMenuEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ShopsRepository extends ElasticsearchRepository<StoreMenuEntity, String> {
}
