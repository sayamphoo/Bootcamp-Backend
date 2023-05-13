package com.boot_camp.Boot_Camp.model.domain;

import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllStoresDomain {
    String id;
    String name;

    public AllStoresDomain(){}
    public AllStoresDomain(MemberEntity e) {
        this.id = e.getIdMember();
        this.name = e.getName();
    }
}
