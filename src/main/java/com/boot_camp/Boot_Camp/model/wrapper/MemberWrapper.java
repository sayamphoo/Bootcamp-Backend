package com.boot_camp.Boot_Camp.model.wrapper;

import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberWrapper implements Cloneable {

    private String idMember;
    private String name;
    private String username;
    private String password;
    private String birthday;
    private boolean store;
    private String sex;
    private int point = 0;

    public MemberWrapper() {}

    public MemberWrapper(MemberEntity e) {
        this.idMember = e.getIdMember();
        this.name = e.getName();
        this.username = e.getUsername();
        this.password = e.getPassword();
        this.birthday = e.getBirthday();
        this.store = e.isStore();
        this.sex = e.getSex();
        this.point = e.getPoint();
    }

    public MemberEntity toEntity() {
        MemberEntity e = new MemberEntity();
        e.setIdMember(this.idMember);
        e.setName(this.name);
        e.setUsername(this.username);
        e.setPassword(this.password);
        e.setBirthday(this.birthday);
        e.setStore(this.store);
        e.setSex(this.sex);
        e.setPoint(this.point);
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
