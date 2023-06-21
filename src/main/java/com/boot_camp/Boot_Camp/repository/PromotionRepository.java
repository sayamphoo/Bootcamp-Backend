package com.boot_camp.Boot_Camp.repository;

import com.boot_camp.Boot_Camp.model.entity.PromotionEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends ElasticsearchRepository<PromotionEntity,String> {

}
