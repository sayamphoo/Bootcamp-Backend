package com.boot_camp.Boot_Camp.repository;

import com.boot_camp.Boot_Camp.model.entity.StoreMenuEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends ElasticsearchRepository<StoreMenuEntity, String> {
    List<StoreMenuEntity> findByAccountId(String s);
    List<StoreMenuEntity> findByCategory(int category);


}

