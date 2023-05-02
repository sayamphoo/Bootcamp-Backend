package com.boot_camp.Boot_Camp.service;

import com.boot_camp.Boot_Camp.entity.domain.MemberDomain;
import com.boot_camp.Boot_Camp.entity.domain.UserDomain;
import com.boot_camp.Boot_Camp.entity.MemberEntity;
import com.boot_camp.Boot_Camp.entity.domain.Point;
import com.boot_camp.Boot_Camp.repository.MemberRepository;
import com.boot_camp.Boot_Camp.security.Security;
import com.boot_camp.Boot_Camp.service.data_structure.Structure;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppService extends Structure {
    @Autowired
    private MemberRepository userRepository;
    @Autowired
    private Security security;

    //-------- Sign In --------------
    public UserDomain singIn(MemberEntity e) {
        UserDomain userDomain = new UserDomain();

        if (e == null) return userDomain;
        MemberEntity entity = getMember(e.getUsername());

        if (entity != null) {
            MemberDomain domain = new MemberDomain(entity);
            domain.setPosition("sd");
            userRepository.save(domain.toEntity());
//dsd
            if (security.encodeHashCompare(e.getPassword(), entity.getPassword())) {
                String token = security.generateToken(
                        resUserClient(
                                entity.getId(),
                                entity.getPosition()
                        )
                );
                userDomain.setCode(StatusCodeEnum.SUCCESS.getValue());
                userDomain.setAccessToken(token);
            }
        }
        return userDomain;
    }

    //-------- Sign Up --------------
    public UserDomain singUp(MemberEntity e) {
        MemberEntity model = new MemberEntity();
        UserDomain userDomain = new UserDomain();
        if (getMember(e.getUsername()) != null) {
            model.setUsername(e.getUsername());
            model.setPassword(security.encodeHash(e.getPassword()));
            model.setPosition("customer");
            model.setBirthday(e.getBirthday());
            model.setSex(e.getSex());
            userRepository.save(model);

            userDomain = this.singIn(e);
        } else {
            userDomain.setCode(StatusCodeEnum.NOT_ACCEPTABLE.getValue());
        }
        return userDomain;
    }

    //-------- Get point for user --------------
    public Point getPoint(String token) {
        Point point = new Point();
        Claims claims = security.readToken(token);
        if (claims != null) {
            Object myId = claims.get("id");
            Optional<MemberEntity> entity = userRepository.findById(myId.toString());
            if (entity.isPresent()) {
                point.setCode(StatusCodeEnum.SUCCESS.getValue());
                point.setPoint(entity.get().getPoint());
            }
        }
        return point;
    }

    //ตรวจสอบว่ามี User อยู่แล้วหรือไม่
    private MemberEntity getMember(String username) {
        return userRepository.findByUsername(username);
    }

    public String validate(String token) {
        return security.readToken(token).toString();
    }


    public List<MemberEntity> getAll() {
        return userRepository.findAll();
    }

    public void transferPoint(String token, String receiverToken, int point) {
        if (security.validateToken(token) && security.validateToken(receiverToken)) {
            if ((!security.readToken(token).isEmpty()) && (!security.readToken(receiverToken).isEmpty())) {

            }
        }
    }
}
