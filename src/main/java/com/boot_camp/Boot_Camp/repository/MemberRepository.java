package com.boot_camp.Boot_Camp.repository;

import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends ElasticsearchRepository<MemberEntity, String> {
    public MemberEntity findByUsername(String username);
    public MemberEntity findByIdMember(String id);
    List<MemberEntity> findByStore(boolean store);
}
