package com.boot_camp.Boot_Camp.entity.domain;

import com.boot_camp.Boot_Camp.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
public class MemberDomain {

    private String username;
    private String password;
    private String birthday;
    private String position;
    private String sex;
    private int point = 0;

    public MemberDomain(){}
    public MemberDomain(MemberEntity memberEntity) {
        this.birthday = memberEntity.getBirthday();
    }

    public MemberEntity toEntity() {
        MemberEntity e = new MemberEntity();
        e.setUsername(this.username);
        return e;
    }

}
