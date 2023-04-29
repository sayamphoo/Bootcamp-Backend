package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.entity.domain.LoginDomain;
import com.boot_camp.Boot_Camp.entity.UserEntity;
import com.boot_camp.Boot_Camp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/showdataall")
    public List<UserEntity> showDataAll() {
        return userService.getAll();
    }

    @PostMapping("/sing_in")
    public LoginDomain singIn(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String pass
    ){
        return userService.singIn(username,pass);
    }

    @PostMapping("/sing_up")
    public LoginDomain singUp(
            @RequestParam("username") String username,
            @RequestParam("password") String pass
    ){
        return userService.singUp(username,pass);

    }

//    @GetMapping("/")
//    public String getMap() {
//        Security security = new Security();
//        return security.generateAccessToken();
//    }

//    @GetMapping("/logintest")
//    public Map<String,String> lsogin(){
//        Map<String ,String> map = new HashMap<String, String>();
//        map.put("Username","lsasa");
//        map.put("pass","lsasa");
//        map.put("point","lsasa");
//        return map;
//    }
}
