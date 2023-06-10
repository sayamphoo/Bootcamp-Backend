package com.boot_camp.Boot_Camp.repository;

import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import com.boot_camp.Boot_Camp.model.wrapper.MemberWrapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MemberRepository extends ElasticsearchRepository<MemberEntity, String> {
    MemberEntity findByUsername(String username);
    MemberEntity findByName(String name);
    MemberEntity findByIdAccount(String id);
    List<MemberEntity> findByStore(boolean store);

    Map<String,String> findNameAndIdAccountByIdAccount(List<String> idAccounts);
}
