package com.boot_camp.Boot_Camp.entity.domain;

import com.boot_camp.Boot_Camp.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDomain {

    private String username;
    private String password;
    private String birthday;
    private String position;
    private String sex;
    private int point = 0;

    public RegisterDomain(){}
    public RegisterDomain(MemberEntity memberEntity) {
        this.birthday = memberEntity.getBirthday();
    }

    public MemberEntity toEntity() {
        MemberEntity e = new MemberEntity();
        e.setUsername(this.username);
        return e;
    }

}
