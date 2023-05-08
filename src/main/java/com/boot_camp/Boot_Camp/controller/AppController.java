package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.entity.domain.GeneratorQrCodeDomain;
import com.boot_camp.Boot_Camp.entity.domain.LoginWrapper;
import com.boot_camp.Boot_Camp.entity.domain.UserDomain;
import com.boot_camp.Boot_Camp.entity.MemberEntity;
import com.boot_camp.Boot_Camp.entity.domain.PointDomain;
import com.boot_camp.Boot_Camp.repository.MemberRepository;
import com.boot_camp.Boot_Camp.service.AppService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user")
public class AppController {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AppService appService;

    @PostMapping("/signIn")
    public UserDomain signIn(@RequestBody(required = false) LoginWrapper e) {
        return appService.singIn(e);
    }

    @PostMapping("/sign_up")
    public UserDomain signUp(@RequestBody(required = false) MemberEntity e) {
        System.out.println("Sign up : " + e.toString());
        return appService.singUp(e);
    }

    @GetMapping("/points")
    public PointDomain getPoint(@RequestParam(value = "token", defaultValue = "") String token) {
        System.out.println("Point : " + token);
        return appService.getPoint(token);
    }


    @GetMapping(path = "generate-qrcode",produces = MediaType.IMAGE_PNG_VALUE)
    public BufferedImage generateQrcode(HttpServletRequest request) {
        return appService.generateQrcode(request.getHeader("verify"));
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
