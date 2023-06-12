package com.boot_camp.Boot_Camp.repository;

import com.boot_camp.Boot_Camp.model.entity.StatisticsMenuEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsMenuRepository extends ElasticsearchRepository<StatisticsMenuEntity, String> {
}
