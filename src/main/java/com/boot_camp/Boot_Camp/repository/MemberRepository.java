package com.boot_camp.Boot_Camp.repository;

import com.boot_camp.Boot_Camp.entity.MemberEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends ElasticsearchRepository<MemberEntity, String> {
//    public UserEntity findByUsername(String userName);

    public MemberEntity findByUsername(String userName);
    public List<MemberEntity> findAll();
}
