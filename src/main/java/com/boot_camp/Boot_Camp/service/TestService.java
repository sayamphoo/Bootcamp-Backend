package com.boot_camp.Boot_Camp.service;

import com.boot_camp.Boot_Camp.model.UserModel;
import com.boot_camp.Boot_Camp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {
    @Autowired
    private UserRepository userRepository;


//    @PostConstruct
//    private void testPostDataCustomer() {
//        List<UserModel> userModel = new ArrayList<UserModel>();
//        for (int i = 0; i < 10; i++) {
//            UserModel user = userRepository.findByUserName("boot_camp@boot.camp.com");
//            userModel.add(user);
//        }
//        userRepository.deleteAll(userModel);
//    }

//    @PostConstruct
//    private void testPostDataCustomer() {
//        System.out.println("Start");
//        UserModel userModel = new UserModel();
//        userModel.setPassword("12345678");
//        userModel.setUserName("boot_camp@boot.camp.com");
//        userModel.setPoint(300);
//        userModel.setPosition("customer");
//        userRepository.save(userModel);
//    }
}
