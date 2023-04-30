package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.entity.domain.LoginDomain;
import com.boot_camp.Boot_Camp.entity.MemberEntity;
import com.boot_camp.Boot_Camp.repository.MemberRepository;
import com.boot_camp.Boot_Camp.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user")
public class AppController {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AppService appService;

    @GetMapping("/showdataall")
//    @SuppressWarnings({ "rawtypes", "unchecked" })

    public List<MemberEntity> showDataAll() {
        return appService.getAll();
    }
    @PostMapping("/sing_in")
    public LoginDomain singIn(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String pass
    ) {
        return appService.singIn(username, pass);
    }

    @PostMapping("/sing_up")
    public LoginDomain singUp(
            @RequestParam(value = "username", defaultValue = "") String username,
            @RequestParam(value = "password", defaultValue = "") String pass
    ) {
        return appService.singUp(username, pass);

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

    @PutMapping("/transfer")
    public void transfer(
            @RequestParam(value = "token") String token,
            @RequestParam(value = "receiverToken") String receiverToken,
            @RequestParam(value = "point") int point
    ) {
        appService.transferPoint(token,receiverToken,point);
    }
}
