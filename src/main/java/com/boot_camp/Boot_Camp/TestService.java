package com.boot_camp.Boot_Camp;

import com.boot_camp.Boot_Camp.entity.UserEntity;
import com.boot_camp.Boot_Camp.repository.UserRepository;
import com.boot_camp.Boot_Camp.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
public class TestService {
    @Autowired
    private UserRepository userRepository;


//    @PostConstruct
//    private void testPostDaftaCustomer() {
//        Security security = new Security();
//        security.encodeHashCompare("sas", "sasa");
//
//    }

//    @PostConstruct
//    private void testPostDaftaCustomer() {
//        List<UserEntity> userModel = userRepository.findAll();
//        userRepository.deleteAll(userModel);
//    }

//
//    @PostConstruct
//    private void testPostDataCustomer() {
//        System.out.println("Start");
//        UserEntity userModel = new UserEntity();
//        userModel.setUsername("Sayampho@boot.camp.com");
//        userModel.setPassword("1911669199");
//        userModel.setPoint(10);
//        userModel.setPosition("customer");
//        userRepository.save(userModel);
//    }
}
