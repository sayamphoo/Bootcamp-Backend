package com.boot_camp.Boot_Camp.repository;

import com.boot_camp.Boot_Camp.model.UserModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends ElasticsearchRepository<UserModel,String> {
    public UserModel findByUserName(String userName);
    public List<UserModel> findAll();
}
