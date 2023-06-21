package com.boot_camp.Boot_Camp.model.domain;

import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import com.boot_camp.Boot_Camp.services.UtilService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllStoresDomain {

    String id;
    String name;

    public AllStoresDomain(){}
    public AllStoresDomain(MemberEntity e) {
        this.id = new UtilService().getIdLocker(e.getId());
        this.name = e.getName();
    }

    public AllStoresDomain(String idLocker,String name) {
        this.id = idLocker;
        this.name = name;
    }
}
