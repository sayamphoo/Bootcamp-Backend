package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.model.UserModel;
import com.boot_camp.Boot_Camp.repository.UserRepository;
import com.boot_camp.Boot_Camp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public List<UserModel> login(){
        return userService.getAll();
    }

    @GetMapping("/logintest")
    public Map<String,String> lsogin(){
        Map<String ,String> map = new HashMap<String, String>();
        map.put("Username","lsasa");
        map.put("pass","lsasa");
        map.put("point","lsasa");

        return map;
    }
//    @GetMapping("/sas")
//    public UserModel lll(){
//        return userRepository.findByUserName("boot_camp@boot.camp.com");
//    }
}
