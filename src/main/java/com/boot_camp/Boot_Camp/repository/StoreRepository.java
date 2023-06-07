package com.boot_camp.Boot_Camp.repository;

import com.boot_camp.Boot_Camp.model.entity.StoreMenuEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface StoreRepository extends ElasticsearchRepository<StoreMenuEntity, String> {
    List<StoreMenuEntity> findByIdAccount(String s);
    List<StoreMenuEntity> findByCategory(int category);
}
