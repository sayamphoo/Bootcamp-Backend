package com.boot_camp.Boot_Camp.service;

import com.boot_camp.Boot_Camp.entity.domain.LoginDomain;
import com.boot_camp.Boot_Camp.entity.MemberEntity;
import com.boot_camp.Boot_Camp.repository.MemberRepository;
import com.boot_camp.Boot_Camp.security.Security;
import com.boot_camp.Boot_Camp.service.data_structure.Structure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class AppService extends Structure{
    @Autowired
    private MemberRepository userRepository;
    @Autowired
    private Security security;

    public List<MemberEntity> getAll() {
        return userRepository.findAll();
    }

    //getMember
    public LoginDomain singIn(String username, String pass) {
        List<MemberEntity> entity = getMember(username);
        LoginDomain loginDomain = new LoginDomain();

        if (!(entity.isEmpty())) {
            if (security.encodeHashCompare(pass, entity.get(0).getPassword())) {
                String token = security.generateToken(resUserClient(entity.get(0).getId(), entity.get(0).getPosition()));
                if (!token.isEmpty()) {
                    loginDomain.setCode(StatusCodeEnum.SUCCESS.getValue());
                    loginDomain.setAccessToken(token);
                }
            }
        }
        return loginDomain;
    }

    //new user สมัครสมาชิก
    public LoginDomain singUp(String username, String pass) {
        MemberEntity entity = new MemberEntity();
        LoginDomain loginDomain = new LoginDomain();

        if (getMember(username).isEmpty()) {
            entity.setUsername(username);
            entity.setPassword(security.encodeHash(pass));
            entity.setPosition("customer");
            entity.setBirthday(new Date().toString());
            userRepository.save(entity);

            String token = security.generateToken(resUserClient(entity.getId(), entity.getPosition()));
            if (!token.isEmpty()) {
                loginDomain.setCode(StatusCodeEnum.SUCCESS.getValue());
                loginDomain.setAccessToken(token);
            }
        } else {
            loginDomain.setCode(StatusCodeEnum.NOT_ACCEPTABLE.getValue());
        }
        return loginDomain;
    }

    //ตรวจสอบว่ามี User อยู่แล้วหรือไม่
    private List<MemberEntity> getMember(String username) {
        return userRepository.findByUsername(username);
    }

    public String validate(String token) {
        return security.readToken(token);
    }

    public void transferPoint(String token,String receiverToken,int point) {
        if (security.validateToken(token) && security.validateToken(receiverToken)) {
            if ((!security.readToken(token).isEmpty()) && (!security.readToken(receiverToken).isEmpty())) {

            }
        }
    }
}
