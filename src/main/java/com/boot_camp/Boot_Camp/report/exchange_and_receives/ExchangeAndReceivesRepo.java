package com.boot_camp.Boot_Camp.report.exchange_and_receives;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ExchangeAndReceivesRepo extends ElasticsearchRepository<ExchangeAndReceivesEntity,String> {
}
