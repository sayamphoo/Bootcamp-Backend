package com.boot_camp.Boot_Camp.model.wrapper;

import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import com.boot_camp.Boot_Camp.services.UtilService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberWrapper implements Cloneable {
    private String name;
    private String username;
    private String password;
    private String birthday;
    private String sex;

    public MemberWrapper() {
    }

    public MemberWrapper(MemberEntity e) {
        this.name = e.getName();
        this.username = e.getUsername();
        this.password = e.getPassword();
        this.birthday = e.getBirthday().toString();
        this.sex = e.getSex();
    }

    public MemberEntity toEntity() {
        MemberEntity e = new MemberEntity();
        e.setName(this.name);
        e.setUsername(this.username);
        e.setPassword(this.password);
        e.setBirthday(new UtilService().coverStrToLocaltime(this.birthday));
        e.setSex(this.sex);
        return e;
    }

    @Override
    public MemberWrapper clone() {
        try {
            return (MemberWrapper) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
