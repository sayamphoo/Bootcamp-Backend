package com.boot_camp.Boot_Camp.repository;

import com.boot_camp.Boot_Camp.model.entity.ImeiEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ImeiRepository extends ElasticsearchRepository<ImeiEntity,String> {
    ImeiEntity findByImei(String imei);
}
