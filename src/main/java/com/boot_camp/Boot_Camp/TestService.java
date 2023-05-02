package com.boot_camp.Boot_Camp;

import com.boot_camp.Boot_Camp.entity.MemberEntity;
import com.boot_camp.Boot_Camp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class TestService {
    @Autowired
    private MemberRepository memberRepository;


//    @PostConstruct
//    private void testPostDaftaCustomer() {
//        MemberEntity point = memberRepository.findById("PsYnzocBog8IPGQ89eRx").get();
//        System.out.println(point.getPoint());
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
