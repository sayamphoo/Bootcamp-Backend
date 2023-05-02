package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.entity.domain.UserDomain;
import com.boot_camp.Boot_Camp.entity.MemberEntity;
import com.boot_camp.Boot_Camp.entity.domain.Point;
import com.boot_camp.Boot_Camp.repository.MemberRepository;
import com.boot_camp.Boot_Camp.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/user")
public class AppController {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AppService appService;

    @PostMapping("/signIn")
    public UserDomain signIn(@RequestBody(required = false) MemberEntity e) {
        System.out.println("Sign in : " + e.toString());
        return appService.singIn(e);
    }

    @PostMapping("/sign_up")
    public UserDomain signUp(@RequestBody(required = false) MemberEntity e) {
        System.out.println("Sign up : " + e.toString());
        return appService.singUp(e);
    }

    @GetMapping("/points")
    public Point getPoint(@RequestParam(value = "token", defaultValue = "") String token) {
        System.out.println("Point : " + token);
        return appService.getPoint(token);
    }

    @PostMapping("/transfer")
    public void transfer(@RequestBody(required = false) Map<String, Object> map) {
        appService.transferPoint(
                map.get("token").toString(),
                map.get("receiverToken").toString(),
                (int) map.get("point"));
    }

    @GetMapping("/validate")
    public String validate(@RequestParam(value = "token", defaultValue = "") String token) {
        return appService.validate(token);
    }

    @GetMapping("/delete")
    public void delete() {
        List<MemberEntity> userModel = memberRepository.findAll();
        memberRepository.deleteAll(userModel);
    }

    @GetMapping("/showdataall")
    public List<MemberEntity> showDataAll() {
        return appService.getAll();
    }


}
