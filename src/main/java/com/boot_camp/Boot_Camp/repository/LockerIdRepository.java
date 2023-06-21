package com.boot_camp.Boot_Camp.repository;

import com.boot_camp.Boot_Camp.model.entity.LockerIdEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LockerIdRepository extends ElasticsearchRepository<LockerIdEntity,String> {
    LockerIdEntity findIdLockerByIdRecord(String idRecord);
    LockerIdEntity findIdRecordByIdLocker(String idLocker);

    LockerIdEntity findByIdLocker(String idLocker);
}
