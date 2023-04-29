package com.boot_camp.Boot_Camp.service;

import com.boot_camp.Boot_Camp.entity.domain.LoginDomain;
import com.boot_camp.Boot_Camp.entity.UserEntity;
import com.boot_camp.Boot_Camp.repository.UserRepository;
import com.boot_camp.Boot_Camp.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private final Security security;

    public UserService() {
        security = new Security();
    }

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    //getMember
    public LoginDomain singIn(String username, String pass) {
        List<UserEntity> entity = getMember(username);
        LoginDomain loginDomain = new LoginDomain();

        if (!(entity.isEmpty())) {
            if (security.encodeHashCompare(pass, entity.get(0).getPassword())) {
//                System.out.println(security.generateAccessToken(entity.get(0)));
                String token = security.generateAccessToken(entity.get(0));
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
        UserEntity entity = new UserEntity();
        LoginDomain loginDomain = new LoginDomain();

        if (getMember(username).isEmpty()) {
            entity.setUsername(username);
            entity.setPassword(security.encodeHash(pass));
            entity.setPosition("customer");
            userRepository.save(entity);

            String token = security.generateAccessToken(entity);
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
    private List<UserEntity> getMember(String username) {
        return userRepository.findByUsername(username);
    }


//    public List<UserModel> delete(){
//        return userRepository.findAll();
//    }

}
