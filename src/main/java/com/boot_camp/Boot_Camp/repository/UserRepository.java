package com.boot_camp.Boot_Camp.repository;

import com.boot_camp.Boot_Camp.entity.UserEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends ElasticsearchRepository<UserEntity, String> {
//    public UserEntity findByUsername(String userName);

    public List<UserEntity> findByUsername(String userName);

    public List<UserEntity> findAll();
}
