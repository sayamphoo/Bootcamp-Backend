package com.boot_camp.Boot_Camp.report.Sale;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SaleAndBuyRepo extends ElasticsearchRepository<SaleAndBuyEntity,String> {
}
