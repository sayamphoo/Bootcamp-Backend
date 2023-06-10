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
        this.id = e.getIdAccount();
        this.name = e.getName();
    }

    public AllStoresDomain(String id,String name) {
        this.id = id;
        this.name = name;
    }
}
